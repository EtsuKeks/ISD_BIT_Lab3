import ru.sber.karimullin.hw_3.Factory.*;
import ru.sber.karimullin.hw_3.Generator.*;

import java.io.*;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        int port = 8080;
        String filePath = "file.txt";

        while(true) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                Socket socket = serverSocket.accept();
                receiveFile(socket, filePath);

                MiniClass obj = null;

                FileInputStream file = new FileInputStream(filePath);
                ObjectInputStream in = new ObjectInputStream(file);

                obj = (MiniClass) in.readObject();

                in.close();
                file.close();
                deleteFile(filePath);

                Generator<MiniClass> generator = Factory.factoryToJSON(obj);
                String jsoned = generator.generate(obj);

                sendToDB(jsoned);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteFile(String filePath) {
        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    private static void receiveFile(Socket socket, String filePath) throws Exception {
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = dis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void sendToDB(String jsoned) {
        String url = "jdbc:mysql://<database_server_ip>:3306/database";
        String username = "user";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String insertQuery = "INSERT INTO table (timestamps, jsoned_objects) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                String timestamp = new Date().toString();
                preparedStatement.setString(1, timestamp);
                preparedStatement.setString(2, jsoned);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}