package com.example.navegacaooficial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public ImageView imageView;
    public Lista<AreaClicavel> list = new Lista(); //lista para guardar as linhas (areas clicaveis) do arquivo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);

        String arquivo = "solar.txt"; //passando nome do arquivo, que está no assets, que deseja fazer a navegação

        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream;

        try {
            inputStream = assetManager.open(arquivo);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            //line = br.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] vect = line.split(",");
                Integer atual = getResources().getIdentifier(vect[0], "drawable", getPackageName());
                Double x1 = Double.parseDouble(vect[1]);
                Double y1 = Double.parseDouble(vect[2]);
                Double x2 = Double.parseDouble(vect[3]);
                Double y2 = Double.parseDouble(vect[4]);
                Integer proximo = getResources().getIdentifier(vect[5], "drawable", getPackageName());


                AreaClicavel areaClicavel = new AreaClicavel(atual, x1, y1, x2, y2, proximo);
                list.inserir(areaClicavel);
            }
            imageView.setImageResource(list.consultar(0).getAtual());
            imageView.setOnTouchListener(new View.OnTouchListener() { //entendendo o clique na imagem
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        //areaClicavel.verificarAreaDoClique(imagemComAreasClicaveis, event.getX(), event.getY());
                        //Obter o Bitmap associado à ImageView
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                        //Obter as dimensões do Bitmap e da ImageView
                        double bitmapWidth = bitmap.getWidth();
                        double bitmapHeight = bitmap.getHeight();
                        double imageViewWidth = imageView.getWidth();
                        double imageViewHeight = imageView.getHeight();

                        //calcular a razão entre as dimensões do Bitmap e da ImageView
                        double ratioX = bitmapWidth / imageViewWidth;
                        double ratioY = bitmapHeight / imageViewHeight;

                        //Aplicar a razão às coordenadas
                        int bitmapX = (int) (event.getX() * ratioX);
                        int bitmapY = (int) (event.getY() * ratioY);
                        verificarAreaDoClique(bitmapX, bitmapY);

                    }
                    return true;
                }
            });

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verificarAreaDoClique(double x, double y) {
        //receber uma lista, x do clique e y do clique
        //verificar cada índice da lista
        for (int k = 1; k < list.size(); k++) {
            if (list.consultar(0).getAtual() == list.consultar(k).getAtual()) {
                if (list.consultar(k).getX1() < x && x < list.consultar(k).getX2()
                        && list.consultar(k).getY1() < y && y < list.consultar(k).getY2()) { //verificando area que foi clicada
                    imageView.setImageResource(list.consultar(k).getProximo());//fazer troca da imagem atual pela proxima
                    break;
                }
            }
            break;
        }

        for (int i = 1; i < list.size(); i++) { //percorrendo a lista
            if (list.consultar(i).getX1() < x && x < list.consultar(i).getX2()
                    && list.consultar(i).getY1() < y && y < list.consultar(i).getY2()) { //verificando area que foi clicada
                imageView.setImageResource(list.consultar(i).getProximo()); //fazer troca da imagem atual pela proxima
                break;
            }
            for (int j = i + 1; j < list.size() - 1; j++) {
                if (list.consultar(i).getProximo() == list.consultar(j).getAtual()) {
                    i = j - 1;
                    break;
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        imageView.setImageResource(list.consultar(0).getAtual());
    }
}