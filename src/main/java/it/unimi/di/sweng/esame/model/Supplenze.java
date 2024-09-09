package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.StringJoiner;

public record Supplenze(@NotNull CodiceIstituto codiceIstituto, int durata,@NotNull String comune,@NotNull LocalDate data) {

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add(comune).add(": dal").add(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).add("-").add(codiceIstituto.toString());
        return sj.toString();
    }

    public String formatString(){
        StringJoiner sj = new StringJoiner(" ");
        sj.add(String.valueOf(durata)).add(": dal").add(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).add("-").add(codiceIstituto.toString());
        return sj.toString();
    }


    public static Comparator<Supplenze> comparatorByDate = new Comparator<Supplenze>() {
        @Override
        public int compare(Supplenze o1, Supplenze o2) {
            int nameComparison = o1.comune.compareTo(o2.comune);
            if (nameComparison != 0) {
                return nameComparison;
            }
            int dateCompare =  o1.data.compareTo(o2.data);
            if (dateCompare != 0) return dateCompare;
            return o1.codiceIstituto.compareTo(o2.codiceIstituto);
        }
    };

    public static Comparator<Supplenze> comparatorByDurata = new Comparator<Supplenze>() {
        @Override
        public int compare(Supplenze o1, Supplenze o2) {
            int durataCmp = Integer.compare(o1.durata,o2.durata);
            if (durataCmp != 0) {
                return durataCmp;
            }
            return  o1.data.compareTo(o2.data);
        }
    };

}
