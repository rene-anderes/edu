package org.anderes.edu.streams;

import java.util.List;

/**
 * Als Beispiel sei angenommen, dass man die Gesamtsumme über die Verkaufspreise einer Menge von Artikeln berechnen,
 * dabei nur Verkaufspreise über einem gewissen Wert berücksichtigen und abschliessend einen zehnprozentigen Rabatt auf
 * die berechnete Summe gewähren möchte.
 */
public class ArraySample {

    private final List<Integer> prices;

    public ArraySample(final List<Integer> prices) {
        super();
        this.prices = prices;
    }

    /**
     * Das Iterable -Interface hat zusätzlich zur iterator -Methode, die es schon immer hatte, eine forEach -Methode
     * bekommen. Die forEach -Methode iteriert über alle Elemente in der Collection und wendet auf jedes Element eine
     * Funktion an, die der Methode als Argument vom Typ Consumer übergeben wird. Die Benutzung der forEach -Methode
     * sieht dann zum Beispiel so aus:
     * </p>
     * List<Integer> numbers = new ArrayList<>();<br>
     * ... populate list ...<br>
     * numbers.forEach( i -> System.out . println (i) );
     * </p>
     * Als Consumer haben wir einen Lambda-Ausdruck übergeben ({@code i -> System.out.println(i)}), der alle
     * Integer-Werte aus der Collection nach System.out ausgibt.
     */
    public void dumpLambda() {
        prices.forEach(i -> System.out.println(i));
    }

    /**
     * Anstelle des Lambda-Ausdruck kann man eine Methoden-Referenz verwenden. Dann sieht es so aus: 
     */
    public void dumpMethodeReferenze() {
        prices.forEach(System.out::println);
    }

    /**
     * Eine Umsetzung vor Java 8 sähe wohl etwa folgendermassen aus (siehe Code).
     * </p>
     * Eigentlich einfach und kurz, aber hier sieht man gleich einige Nachteile:
     * <ul>
     * <li>Der Entwickler muss sich um Initialisierung und explizites Aufsummieren der Variablen totalCost selbst
     * kümmern.</li>
     * <li>Das Beispiel ist in Form einer externen Iteration implementiert, das heißt, die Iteration mit ihren
     * Verarbeitungsschritten obliegt vollständig dem Entwickler.</li>
     * <li>Die Verarbeitung dieses externen Iterators ist inhärent sequenziell. Wer den Code parallelisieren will, muss
     * – unabhängig vom Aufwand, den Parallelisierungscode selbst zu schreiben – den Ablauf selbst stark modifizieren.</li>
     * <li>Innerhalb der Iteration werden verschiedene grundlegende Operationen genutzt: Der Entwickler führt eine
     * Filterung durch (Zeile 'Filter'), reduziert den Preis um den Rabatt und addiert den resultierenden Verkaufspreis
     * zur Gesamtsumme (Zeile 'Gesamtsumme'). Das alles ist fest im Beispiel kodiert. Wer die Logikteile etwa zur
     * Filterung an anderer Stelle wiederverwenden will, muss den entsprechenden Code entweder in neue Methoden
     * auslagern oder duplizieren. Externe Iteratoren gelten daher als schlecht zusammensetzbar, da die individuellen
     * Verarbeitungsschritte innerhalb der Schleife explizit ausprogrammiert sind.</li>
     * </ul>
     */
    public double getTotalCost7() {
        double totalCost = 0.0; // Initialisierung
        for (Integer price : prices) { // externe Iteration
            if (price >= 42) { // Filter
                totalCost += price * 0.9; // Rabatt und Gesamtsumme
            }
        }
        return totalCost;
    }

    /**
     * Wie kann nun ein deklarativer Ansatz von Java 8 an der Stelle helfen? </p> Auch ohne Detailkenntnisse zu den
     * neuen Java-8-Streams ist zu erkennen, welche Arbeitsschritte Teil des Beispiels sind:<br>
     * <ol>
     * <li>Der deklarative Ansatz bringt den Ablauf kurz, klar und gut lesbar zum Ausdruck.</li>
     * <li>Die Hilfsvariable samt Initialisierung entfällt vollständig. Weil der Stream die Iteration selbst übernimmt,
     * entfällt die Notwendigkeit einer for-Schleife mit Zählvariablen beziehungsweise einer for-each-Schleife. Das
     * Beispiel führt also keinen expliziten Zustand (in der Variable totalCost) mit, sondern arbeitet mit den
     * Möglichkeiten des Streams.</li>
     * <li>Auf dem Stream arbeitet der Entwickler mit generischen Methoden wie Stream.filter und Stream.mapToDouble. Die
     * Parametrisierung dieser generischen Methoden erfolgt jeweils über einen anwendungsspezifischen Lambda-Ausdruck
     * (hier für Filter und für Rabattierung).</li>
     * <li>Man spricht hier von interner Iteration, da der Stream selbst die Verantwortung hat, die entsprechenden
     * Operationen auf seinen Elementen auszuführen.</li>
     * </ol>
     * </p> Das Vorgehen hat verschiedene Vorteile:<br>
     * <ul>
     * <li>deklarative Programmierung: Man folgt durch das Verwenden generischer Methoden und deren Parametrisierung
     * durch einen Lambda-Ausdruck dem sogenannten Code-as-Data-Entwurfsmuster. Es stellt neben Vererbung und Delegation
     * eine zusätzliche Möglichkeit zur Abstraktion dar.</li>
     * <li>gute Lesbarkeit: Durch das Nutzen generischer, parametrierbarer Methoden erreicht man eine aussagekräftige
     * Einteilung in funktionale Aspekte. Es ist sofort ersichtlich, dass die Stream-Verarbeitung aus drei wesentlichen
     * Schritten besteht: Filterung, Transformation, Reduktion (Filter-Map-Reduce). Sowohl Kontroll- als auch Datenfluss
     * durch die einzelnen Verarbeitungsschritte sind ersichtlich und gut lesbar. Die verwendeten Lambda-Ausdrücke kann
     * der Entwickler auslagern und über Methodenreferenzen an die generischen Methoden übergeben, um Komponierbarkeit
     * zu erreichen.</li>
     * <li>einfache Syntax: Im Sinne funktionaler Programmiersprachen sind alle Bestandteile Ausdrücke und liefern
     * deswegen ein Ergebnis zurück. Diese lassen sich auch syntaktisch gut aneinanderreihen im Sinne einer
     * "fluent API".</li>
     * <li>mögliche Parallelisierung: Entwickler überlassen dem Stream die Art und Weise der Traversierung seiner
     * Elemente. Demnach kann dessen Implementierung fortgeschrittene Programmiertechniken wie Parallelisierung selbst
     * verantworten. Und tatsächlich bietet die Streams API eine Parallelverarbeitung mit entsprechenden
     * Laufzeitgewinnen an (Details dazu später).</li>
     * <li>mögliche späte Evaluierung: Der Stream kann entscheiden, wann eine Evaluierung der Ausdrücke sinnvoll ist –
     * diese kann insbesondere verzögert ("lazy") passieren.</li>
     * </ul>
     */
    public double getTotalCost8() {
        return prices.stream() // interne Iteration
                .filter(price -> price >= 42) // Filter
                .mapToDouble(price -> price * 0.9) // Rabatt
                .sum(); // Gesamtsumme
    }
}
