package Consola;
import javax.swing.*;
import java.awt.*;
import Inventario.Inventario;
import SistemaAlquiler.*;


public class DisponibilidadCarros extends JPanel{
	
	
    private int[] data; // Datos para el histograma
    private String[] labels; // Etiquetas para cada columna

    public DisponibilidadCarros(int[] data, String[] labels) {
        this.data = data;
        this.labels = labels;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int barWidth = getWidth() / data.length; // Ancho de cada barra
        int maxValue = getMaxValue(data);
        int marginTop = 20;

        for (int i = 0; i < data.length; i++) {
            int barHeight = (int) (((double) data[i] / maxValue) * getHeight());
            int x = i * barWidth;
            int y = getHeight() - barHeight - marginTop;

            // Dibujar la barra
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, y, barWidth, barHeight);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth, barHeight);

            // Dibujar la etiqueta debajo de la columna
            g2d.setColor(Color.BLACK);
            String label = labels[i];
            int labelX = x + barWidth / 2 - g2d.getFontMetrics().stringWidth(label) / 2;
            int labelY = getHeight() - 5;  // Ajuste aquí para posicionar las etiquetas
            g2d.drawString(label, labelX, labelY);
        }
    }

    private int getMaxValue(int[] array) {
        int maxValue = Integer.MIN_VALUE;
        for (int value : array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
  
}
