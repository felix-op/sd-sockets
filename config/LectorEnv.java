package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LectorEnv {
    private final Map<String, String> env = new HashMap<>();

    public LectorEnv(String ruta) {
        cargar(ruta);
    }

    private void cargar(String ruta) {
        Path path = Path.of(ruta);
        if (!Files.exists(path)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                // Ignorar líneas vacías o comentarios
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                String[] partes = linea.split("=", 2);
                if (partes.length == 2) {
                    String clave = partes[0].trim();
                    String valor = partes[1].trim();

                    // Quitar comillas si el valor viene entrecomillado
                    if ((valor.startsWith("\"") && valor.endsWith("\"")) ||
                        (valor.startsWith("'") && valor.endsWith("'"))) {
                        valor = valor.substring(1, valor.length() - 1);
                    }
                    env.put(clave, valor);
                }
            }
        } catch (IOException e) {
            System.err.println("Aviso: no se pudo leer el archivo .env (" + e.getMessage() + ")");
        }
    }

    public String get(String clave, String valorPorDefecto) {
        return env.getOrDefault(clave, valorPorDefecto);
    }

    public int getInt(String clave, int valorPorDefecto) {
        String val = env.get(clave);
        if (val == null) {
            return valorPorDefecto;
        }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return valorPorDefecto;
        }
    }
}
