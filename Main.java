import config.LectorEnv;

public class Main {
    public static void main(String[] args) {
        LectorEnv config = new LectorEnv(".env");

        // Defaults si la variable no está en el .env
        String serverId = config.get("SERVER_ID", "default-node");
        int listenPort = config.getInt("LISTEN_PORT", 5000);

        System.out.printf("servidor %s, puerto %d%n", serverId, listenPort);
    }
}
