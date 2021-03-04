[![Build Status](https://travis-ci.com/inf112-v21/Trivial-Solution.svg?branch=master)](https://travis-ci.com/inf112-v21/Trivial-Solution) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/59c74c9604594cb0a07585f2dd1d4f45)](https://www.codacy.com/gh/inf112-v21/Trivial-Solution/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inf112-v21/Trivial-Solution&amp;utm_campaign=Badge_Grade)

## Beskrivelse av prosjektet
Roborally er et spill som går ut på at hver spiller skal kontrollere en robot ved hjelp av game-cards. Disse kortene forteller hvordan roboten skal bevege seg, slik at den kan prøve å manøvrere gjennom en hektisk fabrikk. Målet med spillet er å danne en strategi som gjør at roboten din kommer først til hvert checkpoint i nummerert rekkefølge. Inne i fabrikken venter også flere hinder, blant annet lasere, hull og bevegende samlebånd som er med på å gjøre det hele en god del vanskeligere.

## Teknisk produktoppsett
Åpne din favoritt-IDE, f.eks. IntelliJ. For å kjøre programmet må man ha innstallert Maven og java versjon må være JDK 15.
Prosjektet vil da bygges, kompileres og kjøres i din valgte IDE. For å få programmet til å kjøre, navigerer du til main-metoden som ligger i  "src/main/java/GUIMain/main", og kjører denne metoden inne i editoren din. Da vil det poppe opp et nytt vindu med spillet. Dette vinduet skal havne øverst, men om du allikevel ikke får opp et nytt vindu, kan det være greit å sjekke om vinduet kan ha havnet bak de andre vinduene du eventuelt har åpne.

## Hvordan spille spillet
Start opp spillet. Klikk singleplayer. Du vil nå bli bedt om å gi et navn til roboten din i terminalen, skriv navnet der og trykk enter. 
Spillet er nå startet, din robot er den helt til høyre, alle robotene er identiske så det må du bare huske. 

For å spille en runde:
- Trykk på vinduet med musen
- Du vil bli bedt om å velge kort i terminalen, velg med å skrive inn heltall der
- Alle 5 fasene vil nå bli simulert umiddelbart, gå tilbake til steg 1 for å fortsette å spille

Noen helt åpenbare ting du som spiller kanskje ser etter, men ikke kommer til å finne:
- Antall liv robotene.
- Retningen robotene peker
- En måte å se forskjell på robotene
Så dette må du også bare holde styr på selv.
