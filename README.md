[![Build Status](https://travis-ci.com/inf112-v21/Trivial-Solution.svg?branch=master)](https://travis-ci.com/inf112-v21/Trivial-Solution) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/59c74c9604594cb0a07585f2dd1d4f45)](https://www.codacy.com/gh/inf112-v21/Trivial-Solution/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inf112-v21/Trivial-Solution&amp;utm_campaign=Badge_Grade)

## Beskrivelse av prosjektet
Roborally er et spill som går ut på at hver spiller skal kontrollere en robot ved hjelp av game-cards. Disse kortene forteller hvordan roboten skal bevege seg, slik at den kan prøve å manøvrere gjennom en hektisk fabrikk. Målet med spillet er å danne en strategi som gjør at roboten din kommer først til hvert checkpoint i nummerert rekkefølge. Inne i fabrikken venter også flere hindre, blant annet lasere, hull og bevegende samlebånd som er med på å gjøre det hele en god del vanskeligere.

## Teknisk produktoppsett
Åpne din favoritt-IDE, f.eks. IntelliJ. For å kjøre programmet må man ha innstallert Maven og java-versjonen må være JDK 15.
Prosjektet vil da bygges, kompileres og kjøres i din valgte IDE. For å få programmet til å kjøre, navigerer du til main-funksjonen som ligger i  "src/main/java/GUIMain/Main", og kjører den inne i editoren. Da vil det poppe opp et nytt vindu med spillet. Dette vinduet skal havne øverst, men om du allikevel ikke får opp et nytt vindu, kan det være greit å sjekke om vinduet kan ha havnet bak de andre vinduene du eventuelt har åpne.

## Hvordan spille spillet
1)  Start opp spillet i IntelliJ. Klikk singleplayer. Velg navn på roboten din, antall spillere, og hvilket kart du vil spille på. 
2)  Når spillet er i gang styrer du den grå roboten som står på to bein, de andre er styrt av maskinen. 
3)  Du vil få utdelt kort du kan velge mellom til høyre på skjermen, klikk på disse for å velge.
4)  Valgte kort dukker opp i en egen kolonne mellom brettet og de tilgjengelige kortene.
5)  Når du er ferdig med å velge kan du klikke på "Ready", og se spillet simulere runden. Gå tilbake til punkt 3.

Noen kritiske bugs/mangler:
- Multiplayer: om man tar powerdown på runden hosten dør så kan ingen spillere ta mot kort og kan ikke spille lengre.
- Host får ikke Gameover hvis den er dø og noen andre vinner.
- Currently connected players viser navn til en disconnected spiller i lobby, men har ingen innvirkning hvis andre vil ha det navnet.
- Får ikke server disconnected når hosten lukker spillet.

- Lykke til!
