package org.anderes.edu.streams;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

import org.junit.Test;

/**
 * Im Zeichen der 8
 *  
 * @see <a href="http://www.angelikalanger.com/Articles/EffectiveJava/71.Java8.Lambdas/71.Java8.Lambdas.html">Effective Java - Java 8 - Lambda Expressions & Method References</a>
 */
public class LambdaBasics {

    /**
     * Im Lambda-Body hat man natürlich Zugriff auf die Parameter und lokale Variablen des Lambda-Ausdrucks. Manchmal
     * möchte man aber auch auf Variablen des umgebenden Kontextes zugreifen. Hier ist ein einfaches Beispiel. Wir
     * verwenden darin den SAM Type IntUnaryOperator aus dem java.util.function -Package.
     * </p>
     * Nur kurz zur Erläuterung: in Zeile //3 machen wir aus einem int -Array einen Stream , dessen map -Methode wir
     * benutzen, um alle Elemente in dem Array mit Hilfe der Funktion times1000 auf einen neuen int -Wert abzubilden und
     * anschließend werden die neuen Werte nach System.out ausgegeben.
     */
    @Test
    public void variableBinding() {

        int factor = 1000; // 1

        IntUnaryOperator times1000 = (int x) -> { return x * factor; }; // 2

        Arrays.stream(new int[] { 1, 2, 3, 4, 5 }).map(times1000).forEach(System.out::println); // 3
    }

    /**
     * Hier taucht die Konstruktor-Referenz als Argument der map -Methode eines Stream<String> vor. Die betreffende map
     * -Methode sieht so aus:
     * </p>
     * <pre>
     * public interface Stream&lt;T&gt; {
     *     &lt;R&gt; Stream&lt;R&gt; map(Function&lt;? super T, ? extends R&gt; mapper);
     * }
     * </pre>
     * </p>
     * Der verwendete SAM-Typ Function sieht so aus:
     * </p>
     * <pre>
     *  {@code @FunctionalInterface}
     *  public interface Function&lt;T, R&gt; {
     *      R apply(T t);
     *  }
     * </pre>
     * </p>
     * In diesem Kontext deduziert der Compiler, dass die Konstruktor-Referenz {@code String Builder ::new} vom Typ
     * {@code Function<String,StringBuilder>} sein muss, also eine Funktion, die einen {@code String} als Argument nimmt
     * und einen {@code StringBuilder} zurück gibt. Es ist also in diesem Kontext der Konstruktor der
     * {@code String Builder}-Klasse gemeint, der einen {@code String} als Argument akzeptiert.
     * Wie man sieht, sind Methoden- und Konstruktor-Referenzen sehr flexibel, weil mit einem einzigen syntaktischen
     * Gebilde wie {@code String Builder ::new} eine ganze Reihe von Methoden bzw. Konstruktoren bezeichnet werden und
     * der Compiler den richtigen von allein herausfindet.
     */
    @Test
    public void ConstructorReference() {

        char[] suffix = new char[] { '.', 't', 'x', 't' };

        Arrays.stream(new String[] { "readme", "releasenotes" })
            .map(StringBuilder::new)
            .map(s -> s.append(suffix))
            .forEach(System.out::println);
    }

}
