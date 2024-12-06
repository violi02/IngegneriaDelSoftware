package it.unimi.di.sweng.esame.model;


import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.*;

public class ModelState implements State {

  private final @NotNull Map<CodiceLibro,Libro> libri = new HashMap<>();
  public void readFile() {
    InputStream is = ModelState.class.getResourceAsStream("/books.csv");
    assert is != null;
    Scanner s = new Scanner(is);

    while (s.hasNextLine()) {
      String linea = s.nextLine();
      String[] el = linea.split(",");
      Libro libro = new Libro(new CodiceLibro(el[0]),el[1],Money.parse(el[2]),StatoLibro.valueOf(el[3]));
      libri.put(libro.codiceLibro(),libro);
    }
  }

  @Override
  public void inVendita(@NotNull CodiceLibro codiceLibro, @NotNull String titolo, @NotNull Money price, @NotNull StatoLibro statoLibro) {
    if (libri.containsKey(codiceLibro)) throw new IllegalArgumentException("Stesso codice per libri diversi");
    libri.put(codiceLibro,new Libro(codiceLibro,titolo,price,statoLibro));
  }

  @Override
  public void buy(@NotNull CodiceLibro codice, @NotNull Email email) {
    if (!libri.containsKey(codice)) throw new IllegalArgumentException("Libro non in vendita");
    libri.remove(codice);
  }

  public List<Libro> getLibri() {
    return new ArrayList<>(libri.values());
  }
}
