#Deloppgave 1:

Navn på gruppe: Trivial Solution

###Kompetanse til medlemmer:
```
Steinar: datateknologi 2 år.
Sander: datasikkerhet/datateknologi 2år
Samuel: IMØ 3 år
Ilyas: datateknologi 2 år
Dusan: datateknologi 2 år

Vi har valgt å skrive inn studielinje og senoritet på studie som kompetanse siden 
vi ikke har så mye annen type erfaring.
```
###Roller:
```
Vi har bestemt oss for at vi kan bytte roller etter hver oblig.
Alle skal få sjansen til å prøve seg på de forskjellige rollene hvis de har lyst.

Akkurat nå har vi disse rollene:
○ Teamleder - Steinar
Vi trenger en Teamleader for å ha et overordnede ansvar i prosjektet.
Arbeidsoppgavene til en teamleader er å se til at de ulike oppgavene 
som vi har fordelt blir gjort innen tidsfristen, og på en måte som fungerer. 
Denne personene vil og lede møtene og sette prioriteringer --> Bestemme hvilke brukerhistoreier som er viktigst. 
Oppgaven til denne personen blir og å tenke på hvordan brukerhistoriene kan bli 
implementert sammen med alle andre.

○ Testansvarlig - Ingen //TODO
Vi trenger en testansvarlig for å dobbelsjekke at testene er gode og at det 
ikke er noen logiske feil i koden. Dette blir viktigere desto lengre vi kommer i 
prosjektet.

○ Referat-ansvarlig - byttet hver gang (Samuel, Steinar, Sander og Dusan)
Vi trenger en referatansvarlig i hvert møte for å skrive ned hva vi har gått igjennom
på det møtet/arbidstiden og hva vi skal gjøre til neste møte.

○ Utvikler - Alle
Vi trenger utviklere i prosjketet for å lage produktet. Oppgaven til utviklerene
er å bli ferdige med de tildelte programmeringsoppgavene innen tidsfristen. 

Senere i prosjektet (Fra oblig 2 og senere):
○ Nettverks-ansvarlig
Vi kommer trenge en netverksansvarlig for å sette det sammen slik at
flere kan spille på en gang. Denne personen/-ene vil da ha det overordnede 
ansvaret for at denne implementasjonen blir implementert.

○ GUI-ansvarlig
Hvis vi skal implementer en fancy GUI mot slutten av prosjektet så kunne 
det være bra å ha en person som er ansvarlig for det også.
```

###Project Board:
```
Vi har valgt å bruke GitHub Project Board.
Her kommer vi sette opp Issues (brukerhistorier) som skal fikses.
Når de er fikset setter vi det til closed(ferdig). Github har en nice 
visuell visning der vi kan se hvor langt vi har kommt (Milestones).
Alle i teamet er komfortable med å bruke det også. 
```

#Deloppgave 2


```
Vi har valgt å bruke Kanban som hoved prosjektmetodikk. Det vi likte med Kanban og grunnen 
til at vi valgte denne metodikken er at målet er å ike produsere et overskudd. Vi bruker da
et Kanban board (github issues boardet) for å visualisere hvor langt vi har komt i prosjektet
og hvilke oppgaver som må gjøres videre.

En annen fin ting med Kanban er at vi tar inn oppgavene som de kommer. Vi holder oss dermed
mer fleksible. Dette setter et større ansvar på teamlederen da, som gjør at denne personen
må følge ekstra godt med her.

Dessuten så tenker vi bruke User-stories fra scrum istedenfor kanban-kort på projectboarden.
Brukerhistorier har spesifikke mål, men de spesifiserer ikke implementasjonen så mye.Målet
vårt er å holde oss så fleksible som mulig.

Ellers så kommer vi bruke TDD fra XP. Vi ser på dette som en god måte å utvikle på og vil prøve den
ut.

Dermed kommer vi også:
-Vi kommer bare til å møtes 2-4 ganger i uken, så det er ekstra viktig å kunne enkelt se hvem som jobber 
med hva, hva som skal gjøres, etc.
-Vi kommer til å variere litt om hva vi jobber med indivudellt og hva vi gjør i par, avhengig av oppgaven. 

Parprogrammering er jo litt mer slitsomt over nettet, men for enkelte oppgaver er det nødvendig for å få det til. 
Teamlederen har ansvar for å dele ut oppgaver, og å følge opp at det blir gjort på en måte som henger sammen med 
resten av prosjektet. 
Hvert møte starter da ved at vi går igjennom hva vi har gjort siden forrige gang, ledet av teamleaderen. Deretter oppdaterer vi issueboardet.
Mellom møtene bruker vi discord til å kommunisere.

Oppgaver som må gjøres:
	1. Finne ut hvordan kort skal representeres. Begynne å skrive kort-klassene
	2. Finne ut hvordan brettet skal representeres, hvor mange lag. Som en grid
	3. Finne ut hvordan en robot skal representeres.
	4. Finne ut hvordan registers skal representeres. (spiller -> register -> robot)
	5. Finne ut hvordan tilbehøret på brettet skal representeres. (lasere, samlebånd, hull, hvilket layer av brettet skal de være på?)

Arbeidsfordeling: 
	1. Samuel
	2. Steinar
	3. Ilyas
	4. Sander
	5. Dusan
```

#Deloppgave 3

    Målet med denne applikasjonen er å lage et spill (RoboRally), som skal kunnes spilles av flere spillere samtidig. Spillet må kunne vise alle brikkene for hver enkelt spiller,
    samt fungere etter reglene som gjelder for spillet.

###Brukerhistorier med akseptansekrav:
    - Som spiller vil jeg kunne se et spillebrett, så jeg enklere kan visualisere hva som skjer. (Krav 1.)
    Akseptansekrav: Fungerende brett vises grafisk på skjermen ved hjelp av en GUI.

	- Som spiller vil jeg kunne se hvor mine og andre spillere sine brikker står, slik at jeg lettere kan planlegge neste trekk. (Krav 2)
		Akseptansekrav: Brettet må kunne vise robot-objektene for spilleren grafisk på skjermen, i korrekte posisjoner på brettet.
        
    - Som utvikler vil jeg kunne flytte brikker ved hjelp av piltaster, for å lettere debugge spillet. Dette skal ikke være tilgjengelig for kunder. (Krav 3)
		Akseptansekrav: piltastene flytter spillerikonet på skjermen. Opp for Nord, ned for Sør, etc.

	- Som spiller vil jeg kunne vinne spillet ved å besøke alle flaggene. (Krav 4,5)
		Akseptansekrav: Spilleren får opp en popup-beskjed på skjermen eller lignende når han har besøkt alle flaggene og dermed vunnet spillet.

	- Som spiller ønsker jeg å kunne spille mot andre mennesker.(Krav 6)
        Akseptansekrav: Spillet må kunne spilles av minst 2 personer ved hjelp av et framework som muliggjør dette. Spillet skal fungere og se likt ut for alle spillere,
        dvs. alle brikker skal vises, alle objekter skal vises, logikken skal fungere osv.

	- Som spiller ønsker jeg å få utdelt kort, slik at jeg kan velge 5 kort for å spille spillet. (Krav 7,8)
		Akseptansekrav: Spillet må kunne dele ut opptil 9 kort til hver spiller, slik at spiller kan velge ut 5 av disse. Disse 5 kortene må kunne bli lagt inn i et register. 
        Hvert register skal kun være tilgjengelig for hver enkelt spiller, og ikke motspillere.

	- Som spiller vil jeg at roboten flyttes basert på de 5 utvalgte kortene mine. (Krav 9)
		Akseptansekrav: Spillet må kunne tolke kortene i rekkefølge slik de er angitt av spilleren. Brikken skal dermed flytter, utifra spill-logiken, på den måten 
        spilleren har bestemt. Dette må skje riktig iforhold til spill-logikken/reglene.

###Prioriterte brukerhistorier for første oblig:
    - Som spiller vil jeg kunne se et spillebrett, så jeg enklere kan visualisere hva som skjer. (Krav 1.)
		Akseptansekrav: Fungerende brett vises på skjermen.

	- Som spiller vil jeg kunne se hvor mine og andre spillere sine brikker skal stå, slik at jeg lettere kan planlegge neste trekk. (Krav 2)
		Akseptansekrav: Brettet må kunne vise robot-objektene for spilleren. 

	- Som spiller vil jeg kunne flytte brikken min på en intuitiv måte. (Krav 3). Som utvikler vil jeg kunne flytte brikker ved hjelp av piltaster,
        for å lettere debugge spillet. Dette skal ikke være tilgjengelig for kunder.
		Akseptansekrav: Brikken skal kunne flyttes rundt på brettet.

	- Som spiller vil jeg kunne vinne spillet ved å besøke alle flaggene. (Krav 4,5)
		Akseptansekrav: Spiller får opp en beskjed når han har besøkt alle flaggene og dermed vunnet spillet.



#Deloppgave 4
1. Vise et spillebrett
2. Vise brikke på spillebrett
3. Flytte brikke (vha taster e.l. for testing)
4. Robot besøker flagg
5. Robot vinner ved å besøke flagg

Vi har valgt å fokusere på disse 5 punktene til oblig1.




#Oppsummering

###Erfaringer:
    - Vanskelig å holde seg til MVP og Kanban, lett å begynne på andre oppgaver uten å helt legge merke til det selv.
    - Enkelte av akseptansekravene våre var upresise, noe som gjorde dem til isfjell.
    - Vi har hatt en jevnt over god kommunikasjon mellom alle deltakere og alle har bidratt like mye til prosjektet :)
    

###Hva vi kan forbedre:
    - Til neste gang må vi bli enige om et språk for commits/kommentarer/kode osv.
    - Skrive opp issues på begynnelsen av hvert møte.
    - Litt klarere roller
    - Vi må begynne å skrive tester
    - Klarere struktur på møter
    - Ha tydeligere fokus. Holde oss mer til Kanban og MVP

###Hvor langt har vi komt:
    -Vi har greid punktene 1-4 fra deloppgave 4 så langt. Vi blir mest sannsynlig
    ferdige med punkt 5 snart også.


    
    

