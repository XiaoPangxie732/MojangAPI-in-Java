/*
 *  MojangAPI-in-Java--Mojang Public API Java implementation.
 *  Copyright (C) 2019-2020  XiaoPangxie732
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package cn.xiaopangxie732.mojang_api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.xiaopangxie732.mojang_api.Status.StatusServer;
import cn.xiaopangxie732.mojang_api.util.Auth;
import cn.xiaopangxie732.mojang_api.util.Net;

/**
 * Used for skin related operations.
 * @author XiaoPangxie732
 * @since 0.0.5
 */
public class Skin {
	/**
	 * This will set the skin for the selected profile, but Mojang's servers will fetch the skin from a URL. This will also work for legacy accounts.
	 * @param access_token The access token of an account. can be get by using {@link Auth#getAccessToken(String, String)}
	 * @param slim Skin is slim or not.
	 * @param uuid UUID of the account. can be get by using {@link UserName#UUIDAtNow(String)}
	 * @param url The skin image path. Cannot be local file.
	 * @throws IllegalArgumentException When change skin failed.
	 * @since 0.0.5
	 */
	public static void changeSkin(String access_token, boolean slim, String uuid, URL url) throws IllegalArgumentException {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		try {
			String response = Auth.postConnection("https://api.mojang.com/user/profile/" + uuid +"/skin", 
					"model=" + (slim ? "slim" : "") + "&url=" + 
			URLEncoder.encode(url.toString(), "UTF-8"), access_token);
			if(!response.equals(""))
				throw new IllegalArgumentException(JsonParser.parseString(response).getAsJsonObject()
						.get("errorMessage").getAsString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This uploads a skin to Mojang's servers. It also sets the users skin. This works on legacy counts as well.
	 * @param access_token The access token of an account. can be get by using {@link Auth#getAccessToken(String, String)}
	 * @param isSlim Skin is slim or not.
	 * @param uuid UUID of the account. can be get be using {@link UserName#UUIDAtNow(String)}
	 * @param uri The skin image path. if it is a local file, it needs add a prefix "file:///" and replace "\\" to "/"(on Windows).
	 * @throws IllegalArgumentException When change skin failed.
	 * @since 0.0.5
	 */
	public static void changeSkinAndUpload(String access_token, boolean isSlim, String uuid, URI uri) 
			throws IllegalArgumentException {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL("https://api.mojang.com/user/profile/" + uuid 
					+"/skin").openConnection());
			connection.setDoOutput(true);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Authorization", "Bearer " + access_token);
			/*
			 * The boundary is Base64-encoded of "MojangAPI-in-Java_datatransfer"
			 */
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(ImageIO.read(new File(uri)), "png", baos);
			connection.getOutputStream().write(("--TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy\r\n" + 
					"Content-Disposition: form-data; name=\"model\"\r\n\r\n" + 
					(isSlim ? "slim" : "") + "\r\n" + 
					"--TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy\r\n" + 
					"Content-Disposition: form-data; name=\"file\"; filename=\"skin.png\"\r\n" + 
					"Content-Type: image/png\r\n\r\n").getBytes("UTF-8"));
			baos.writeTo(connection.getOutputStream());
			connection.getOutputStream().write(("\r\n" + "--TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy--")
					.getBytes("UTF-8"));
			connection.connect();
			try(InputStream in = connection.getInputStream()) {
				for(int i = in.read(); i != -1; i = in.read()) 
					response.append((char)i);
			}
		} catch(IOException ioe) {
			try(InputStream err = connection.getErrorStream()) {
				for(int i = err.read(); i != -1; i = err.read()) 
					response.append((char)i);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	/**
	 * Reset the skin.
	 * @param access_token The access token of UUID's account. can be get by using {@link Auth#getAccessToken(String, String)}
	 * @param uuid UUID of the player. can be get be using {@link UserName#UUIDAtNow(String)}
	 * @throws IllegalStateException Throw when change skin failed.
	 * @since 0.0.5
	 */
	public static void resetSkin(String access_token, String uuid) {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL("https://api.mojang.com/user/profile/" + uuid 
					+"/skin").openConnection());
			connection.setDoOutput(true);
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Authorization", "Bearer " + access_token);
			connection.connect();
			try(InputStream in = connection.getInputStream()) {
				for(int i = in.read(); i != -1; i = in.read()) 
					response.append((char)i);
			}
		} catch(IOException ioe) {
			try(InputStream err = connection.getErrorStream()) {
				for(int i = err.read(); i != -1; i = err.read()) 
					response.append((char)i);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw new IllegalArgumentException(JsonParser.parseString(response.toString()).getAsJsonObject()
					.get("errorMessage").getAsString());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	/**
	 * Used to verify identity when changing skin with untrusted IP.
	 * @author XiaoPangxie732
	 * @since 0.1
	 */
	public static class SecurityQA {
		/**
		 * Security question.
		 * @author XiaoPangxie732
		 * @since 0.1.1
		 */
		public static class Question {
			private int id;
			private String questionName;
			private Answer answer;
			Question(int id, String questionName, Answer answer) {
				this.id = id;
				this.questionName = questionName;
				this.answer = answer;
			}
			/**
			 * Get ID of the question.
			 * @since 0.1.1
			 * @return ID of the question.
			 * @see <a href="https://wiki.vg/Mojang_API#Get_list_of_questions">Possible ID and Questions</a>
			 */
			public int getId() {
				return id;
			}
			/**
			 * Get name of the question.
			 * @since 0.1.1
			 * @return Name of the question.
			 * @see <a href="https://wiki.vg/Mojang_API#Get_list_of_questions">Possible ID and Questions</a>
			 */
			public String getQuestionName() {
				return questionName;
			}
			/**
			 * Get Answer object of this question.
			 * @since 0.1.1
			 * @return Answer object of this question.
			 */
			public Answer getAnswer() {
				return answer;
			}
		}
		/**
		 * Answer of the security question.
		 * @author XiaoPangxie732
		 * @since 0.1.1
		 */
		public static class Answer {
			private int id;
			private String answer;
			Answer(int id) {
				this.id = id;
				answer = null;
			}
			/**
			 * Set answer of the questions, required
			 * @param answer Answer of the question
			 * @return This object.
			 * @since 0.1.1
			 */
			public Answer setAnswer(String answer) {
				this.answer = answer;
				return this;
			}
			JsonObject getJson() {
				JsonObject obj = new JsonObject();
				obj.addProperty("id", id);
				obj.addProperty("answer", Objects.requireNonNull(answer, "Answer could not be null"));
				return obj;
			}
		}
		/**
		 * Check if you need to answer security questions
		 * @param accessToken Player's access token
		 * @return true for need and false for not need
		 * @see Auth#getAccessToken(String, String)
		 * @since 0.1
		 */
		public static boolean checkNeeded(String accessToken) {
			Status.ensureAvailable(StatusServer.API_MOJANG_COM);
			HashMap<String, String> map = new HashMap<>(1);
			map.put("Authorization", "Bearer " + accessToken);
			if(Net.getConnection("https://api.mojang.com/user/security/location", map).length() == 0) return false;
			else return true;
		}
		/**
		 * Get security questions must to answer.
		 * @param accessToken Player's access token.
		 * @return List of question you need answer.Total of 3.
		 * @see Auth#getAccessToken(String, String)
		 * @since 0.1.1
		 */
		public static ArrayList<Question> getQuestionList(String accessToken) {
			Status.ensureAvailable(StatusServer.API_MOJANG_COM);
			HashMap<String, String> map = new HashMap<>(1);
			map.put("Authorization", "Bearer " + accessToken);
			ArrayList<Question> questions = new ArrayList<>(3);
			JsonParser.parseString(Net.getConnection("https://api.mojang.com/user/security/challenges", map))
			.getAsJsonArray().forEach(ele -> {
				JsonObject obj = ele.getAsJsonObject().get("question").getAsJsonObject();
				questions.add(new Question(obj.get("id").getAsInt(), obj.get("question").getAsString(), 
					new Answer(ele.getAsJsonObject().get("answer").getAsJsonObject().get("id").getAsInt())));
			});
			return questions;
		}
		/**
		 * Send back the answers of security questions to verify your IP.
		 * @param accessToken Player's access token.
		 * @param answers The answers of questions.
		 * @throws IllegalArgumentException When at least one answer is incorrect.
		 * @throws IllegalArgumentException When the amount of the answers more than 3 or less than 3.
		 * @since 0.1.1
		 */
		public static void sendBackAnswers(String accessToken, ArrayList<Answer> answers) throws IllegalArgumentException {
			if(answers.size() != 3) throw new IllegalArgumentException("Amount of the answer can only be 3");
			Status.ensureAvailable(StatusServer.API_MOJANG_COM);
			HashMap<String, String> map = new HashMap<>(1);
			map.put("Authorization", "Bearer " + accessToken);
			JsonArray reqVar = new JsonArray(3);
			answers.forEach(a -> reqVar.add(a.getJson()));
			String response = Net.postConnection("https://api.mojang.com/user/security/location", 
					"application/json", reqVar.toString(), map);
			if(response.length()>0) throw new IllegalArgumentException(JsonParser.parseString(response)
					.getAsJsonObject().get("errorMessage").getAsString());
		}
	}
}