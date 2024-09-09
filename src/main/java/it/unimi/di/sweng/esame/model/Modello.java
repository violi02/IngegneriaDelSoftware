package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Modello  implements State{

  private final @NotNull Map<CodiceIstituto,Set<LocalDate>> corrisp = new HashMap<>();

  private final @NotNull List<Supplenze> supplenze = new ArrayList<>();

  public void readFile() {
    InputStream is = Modello.class.getResourceAsStream("/reports.csv");
    assert is != null;
    Scanner s = new Scanner(is);

    while (s.hasNextLine()) {
      String linea = s.nextLine();
      String[] el = linea.split(":");

      // memorizzare quanto letto
      CodiceIstituto codiceIstituto = new CodiceIstituto(el[0]);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate date = LocalDate.parse(el[3], formatter);
      corrisp.put(codiceIstituto,new HashSet<>());
      corrisp.get(codiceIstituto).add(date);

      supplenze.add(new Supplenze(codiceIstituto,Integer.parseInt(el[1]),el[2],date));

      System.err.println(linea);
    }
  }

  @Override
  public void inserisciSupplenza(@NotNull CodiceIstituto codiceIstituto, int durata, @NotNull String comune, LocalDate data) {
    if (corrisp.containsKey(codiceIstituto)) corrisp.get(codiceIstituto).add(data);
    else {
      corrisp.putIfAbsent(codiceIstituto,new HashSet<>());
      corrisp.get(codiceIstituto).add(data);
    }
    supplenze.add(new Supplenze(codiceIstituto,durata,comune,data));
  }

  @Override
  public void accettaSupplenza(@NotNull CodiceInsegnante codiceInsegnante, @NotNull CodiceIstituto codiceIstituto, LocalDate data) {
    boolean flag = false;
    for (Map.Entry<CodiceIstituto, Set<LocalDate>> codiceIstitutoSetEntry : corrisp.entrySet()) {
      if (codiceIstitutoSetEntry.getKey().equals(codiceIstituto)){
         if (codiceIstitutoSetEntry.getValue().contains(data)) {
           flag = true;
           corrisp.remove(codiceIstituto);
         }
      }
    }
    if (flag){
      for (Supplenze supplenze1 : supplenze) {
        if (supplenze1.codiceIstituto().equals(codiceIstituto) && supplenze1.data().equals(data)) {
          supplenze.remove(supplenze1);
          break;
        }
      }
    }
  }

  public List<Supplenze> getState() {
    return new ArrayList<>(supplenze);
  }
}
