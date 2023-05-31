package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String url = "https://www.promiedos.com.ar/ayer";

        try {
            // Fetch the HTML content of the webpage
            Document document = Jsoup.connect(url).get();

            // Find the div element with the id "partidos"
            Element divElement = document.selectFirst("div#partidos");
            System.out.println(divElement);
            if (divElement != null) {
                // Find all the match elements within the div
                Elements matchElements = divElement.select("tbody");
                System.out.println("12" + matchElements);
                for (Element matchElement : matchElements) {
                    // Extract the necessary information from each match element
                    String score1 = matchElement.select(".game-r1").get(1).text();
                    String score2 = matchElement.select(".game-r2").get(1).text();
                    String league = matchElement.selectFirst(".tituloin").text();
//                    String date = matchElement.selectFirst(".fecha").text();
                    String team1 = matchElement.select(".datoequipo").get(0).text();
                    String team2 = matchElement.select(".datoequipo").get(1).text();
                    String playersString = matchElement.selectFirst(".goles").text();

                    // Extract player names and times using regular expressions
                    StringBuilder scorerBuilder = new StringBuilder();
                    Pattern pattern = Pattern.compile("(\\d+)' ([^;]+);");
                    Matcher matcher = pattern.matcher(playersString);

                    while (matcher.find()) {
                        String time = matcher.group(1);
                        String player = matcher.group(2);
                        scorerBuilder.append(time).append("' ").append(player).append(", ");
                    }

                    String scorer = scorerBuilder.toString().trim();
                    if (scorer.endsWith(",")) {
                        scorer = scorer.substring(0, scorer.length() - 1);
                    }

                    System.out.println("League: " + league);
//                    System.out.println("Date: " + date);
                    System.out.println("Match: " + team1 + " (" + score1 + ") vs " + team2 + " (" + score2 + ")");
                    System.out.println("Score: " + score1 + " " + score2);
                    System.out.println("Scorer: " + scorer);
                    System.out.println("-----------------------------");
                }
            } else {
                System.out.println("No div element with id 'partidos' found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}