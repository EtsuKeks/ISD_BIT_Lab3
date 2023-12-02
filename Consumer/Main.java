import ru.sber.karimullin.hw_3.Factory.*;
import ru.sber.karimullin.hw_3.Generator.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        int port = 12345;

        while(true) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                String filePath = "received_file.txt";
                Socket socket = serverSocket.accept();
                receiveFile(socket, filePath);

                MiniClass obj = null;

                FileInputStream file = new FileInputStream(filePath);
                ObjectInputStream in = new ObjectInputStream(file);

                obj = (MiniClass) in.readObject();

                in.close();
                file.close();

                Generator<MiniClass> generator = Factory.factoryToJSON(obj);
                String jsoned = generator.generate(obj);




            } catch (Exception e) {
                e.printStackTrace();
            }
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


//        Random rand = new Random();
//        Date date = new Date();
//        String userName = generateString();
//        String password = generateString();
//        List<Integer> someNumbers = new ArrayList<>();
//        someNumbers.add(rand.nextInt(100) + 100);
//        someNumbers.add(rand.nextInt(100) + 100);
//        someNumbers.add(rand.nextInt(100) + 100);
//
//        MiniClass obj = new MiniClass(date, userName, password, someNumbers);
//        Generator<MiniClass> generator = Factory.factoryToJSON(obj);
//        System.out.print(generator.generate(obj));
//    }
//
//    public static String generateString() {
//        String characters = "qwerty123456";
//        Random rng = new Random();
//        int length = rng.nextInt(10) + 3;
//        char[] text = new char[length];
//        for (int i = 0; i < length; i++)
//        {
//            text[i] = characters.charAt(rng.nextInt(characters.length()));
//        }
//        return new String(text);
//    }
}