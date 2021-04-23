# Oblig-4:
## Deloppgave 1

### Møtereferater finnes i mappen Møte-referat.

### Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?
- Ganske fint, vi beholder de vi har valgt med noen tweaks.
- Altså Steinar er teamleader, Sander er testansvarlig og Dusan er nettverksansvarlig. Det som er nytt er at Samuel er GUI-ansvarlig
- Klikk på ‘Credits’ in-game for en “full oversikt”.

### Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne?
- Å ha alt fint og oversiktlig på project-boardet er veldig nyttig. Det gjør det enkelt å samarbeide, og vite hva som skal bli gjort.
- Vi har nevnt dette tidligere men Kanban som prosjektmetodikk har fungert bra for oss. Den er fleksibel og dekker våre behov, så vi kommer fortsette å bruke den.
Basically ingen nye erfaringer fra Oblig 3 når det kommer til prosjektmetodikk.

### Synes teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?
- Teamet synes denne prosjektmetodikken har fungert veldig bra. Det er verdt å nevne at vår prosjektmetodikk følger en strukturet plan som finnes på issues-siden. 
- Intet nytt her heller.
 
### Gjør et retrospektiv hvor dere vurderer prosjektet har gått. Hva har dere gjort bra, hva hadde dere gjort annerledes hvis dere begynte på nytt? 
- Vi er ferdige! Jippi.
- Vi har valgt å fokusere først på backend/spillogikk, deretter GUI/singleplayer, og så Multiplayer til slutt.  Vi har dermed bygget opp prosjektet fra bunnen av. Det høres veldig enkelt ut, men det er tydeligvis veldig radikalt i forhold til de andre gruppene vi har snakket med. Det har fungert veldig bra, og vi er veldig fornøyde med produktet vi har endt opp med.
- Det er ikke så mye vi hadde gjort annerledes, men om vi hadde begynt på nytt hadde vi visst mye mer om Libgdx og hadde dermed fått gjort mye mer på kortere tid. Da kunne vi lagt til flere features, f. eks bonuskort og full online multiplayer.

### Hvordan fungerer gruppedynamikken og kommunikasjonen nå i forhold til i starten? 
- Vi har generelt mye mer definerte ekspertisefelt nå enn før. I starten var alle sammen backend-code monkeys, men nå mot slutten har vi spesialisert oss i mer spesifikke ting. Nå har vi en GUI-design-ekspert, en multiplayer-ekspert, en animasjonsekspert. Steinar (team-lead) har holdt seg litt mer som potet, siden han implementerte mesteparten av spill-logikken, så vi har måttet kommunisere med han når vi skulle sette ting sammen. 
- Ellers så har det vært en sunn diskusjon om ideer på kodeløsninger og implementasjon av nye funksjonaliteter. Alle er villig til å nevne om noe må gjøres: bedre eller enklere (kan legges til fler). Vi opprettholder demokratiet hvor Steinar hovedsakelig legger til issues (Dusan har begynt å legge til issues siden sist gang også) som må jobbes med og alle velger selv hvilken issue de vil jobbe med basert på prioritetsnivået.

###Legg ved skjermdump av project board ved innlevering. Sørg for at det er oppdatert med siste status ved innlevering
- Se bildet i delivarables.
 
## Deloppgave 2

### Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang

Hva har vi gjort siden forrige gang:
- Reworket hele GUI-en, den ser kjempefin ut nå :)
- Implementert LAN-basert multilplayer (som dessverre ikke funker på eduroam, men det funker på vanlige hjemmenettverk).
- Lasere vises nå dynamisk på skjermen når de blir skutt
- Roboter ‘blinker’ når de tar skade, super mario-style.

Det som gjenstår:
- Optioncards
- Det er veldig mange måter man kan disconnecte på i multiplayer, og veldig mange edge cases generelt. Vi har forsøkt å dekke alle, men det er


### For hvert krav dere jobber med, må dere lage 1) ordentlige brukerhistorier, 2) akseptansekriterier og 3) arbeidsoppgaver. Husk at akseptansekriterier ofte skrives mer eller mindre som tester 
- Se prosjektboardet på github.

### Dersom dere har oppgaver som dere skal til å starte med, hvor dere har oversikt over både brukerhistorie, akseptansekriterier og arbeidsoppgaver, kan dere ta med disse i innleveringen også. 
- Prosjektboardet på Github oppdateres konstant, så stikk innom der når som helst for å se hva vi har jobbet med/jobber med/planlegger å jobbe med.SJekk done-kolonnen, ey.

### Husk å skrive hvilke bugs som finnes i de kravene dere har utført (dersom det finnes bugs).
- Spillvinduet lukkes ikke ordentlig om man prøver å krysse det.

### Kravlista er lang, men det er ikke nødvendig å levere på alle kravene hvis det ikke er realistisk. Det er viktigere at de oppgavene som er utført holder høy kvalitet. Utførte oppgaver skal være ferdige (spillbare).
- Eh ja, I guess.
- …sorry, hva var spørsmålet?

    
## Deloppgave 3
### Dere må dokumentere hvordan prosjektet bygger, testes og kjøres, slik at det er lett for gruppelederne å bygge, teste og kjøre koden deres. Under vurdering kommer koden også til å brukertestes.
- Se README-filen.

### Prosjektet skal kunne bygge, testes og kjøres på Linux, Windows og OSX. 
Det gjør det, ifølge Travis.

### Lever klassediagram
- Se bildet i Deliverables.

### Kodekvalitet og testdekning vektlegges. Merk at testene dere skriver skal brukes i produktet. Det kan være smart å skrive manuelle tester for å teste det som er grafisk. 
- Se testmappen.

### Hvis dere tester manuelt: lever beskrivelser av hvordan testen foregår, slik at gruppeleder kan utføre testen selv. 
- Vi har en ny testklasse MultiplayerGUITest, der vi basically har copypastet main-funksjonen vår inn 3 ganger. Dermed kan vi kjøre 4 parallelle spill (altså ha en host og 3 klienter). Nyttig for testing av multiplayer.

### Under vurdering vil det vektlegges at alle bidrar til kodebasen. Hvis det er stor forskjell i hvem som committer, må dere legge ved en kort forklaring for hvorfor det er sånn. Husk å committe alt. (Også designfiler) 
- Forskjellen i antall commits av hver person skyldes blant annet mangelen av bruk av branches i begynnelsen av prosjektet, noe som førte til at merging gikk galt og overførte kodelinjer til feil personer.
- Skinnet som blir brukt av GUI-en var i seg selv 2500 linjer.
- Automatisk refactoring i IntelliJ ga også over 1000 linjer i en commit.
- Vi har nå prøvd å variere litt i hvem som committer arbeidet gjort under parprogrammering.
- sco008 og Samuel er samme person, vi aner ikke hvorfor halvparten av commitene hans blir på det andre navnet.

### Prosjektpresentasjon teller ved denne leveransen (holdes etter dere har levert). Dere skal ha med demo av spillet, en beskrivelse av det viktigste dere har lært, ville dere gjort noe annerledes om dere hadde gjort det igjen, samt en liten vurdering av hvordan det ville vært å gjennomføre prosjektet med fysisk tilstedeværelse

