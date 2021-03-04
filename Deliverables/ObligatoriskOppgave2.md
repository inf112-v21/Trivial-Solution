Deloppgave 1: Prosjekt og prosjektstruktur

Hvordan fungerer rollene i teamet?
Rollene i teamet fungerer bra.

Trenger dere å oppdatere hvem som er teamlead eller
kundekontakt?
Vi trenger oppdattere rollene litt, men vi beholder en lignende roller som forrige gang.

De rollene som er de samme som forrige gang:
-Teamleader --> Steinar
-Utvilkere --> Alle
-Referatansvarlig --> Vi rulere på denne hver gang og tar tur å skrive referat.

De nye rollene vi introduserer fra nå:
○ Nettverks-ansvarlig - Dusan
Vi kommer trenge en nettverksansvarlig for å sette det sammen slik at
flere kan spille på en gang. Denne vil da ha det overordnede
ansvaret for at denne implementasjonen blir implementert.

○ GUI-ansvarlig - Ilyas
Hvis vi skal implementere en fancy GUI mot slutten av prosjektet så kan
det være bra å ha en person som er ansvarlig for det også. Ilyas har hovedansvaret
for at dette blir implementert.

Personene som er ansvarlige for disse kan delegere oppgavene videre hvis de trenger hjelp

○ Testansvarlig - Sander
Vi trenger en testansvarlig for å dobbelsjekke at testene er gode og at det
ikke er noen logiske feil i koden. Dette blir viktigere desto lengre vi kommer i
prosjektet. Sander har hovedansvaret for at dette blir implementert.


Rollene vi ikke trenger:
Kundeansvarlig -- Utdypes senere





Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes
teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre
måten teamet fungerer på?

Vi synes at team-metodikken vi har valgt(Kanban) har funket veldig bra. Vi kunne ikke hatt Scrummøter hver eneste morgen
så vi er glade for at vi valgte Kanban. Dessuten så har alle i gruppen andre forpliktelser også, så de kan ta færre issues den uken.
Alle velger issuene de vil jobbe med selv. (Utdype)




Hvordan er gruppedynamikken?
Vi synes at kommunikasjonen fungerer bra. Vi har snakket med hverandre i løpet av prosjektet og spesielt i møtene.
Her har vi forklart planene våre. Alle har vært enige i hvilken rettning vi skal gå, og veldig få misforståelser.



Hvordan fungerer kommunikasjonen for dere?
Kommunikasjonen fungerer veldig bra. Vi har ikke feilet å kommunisere.


Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres. Dette
skal handle om prosjektstruktur, ikke kode. Dere kan selvsagt diskutere kode, men dette handler ikke
om feilretting, men om hvordan man jobber og kommuniserer.

Vi har valgt å bruke Kanban som hoved prosjektmetodikk og det har som sagt virket bra. Det vi likte med Kanban og grunnen
til at vi valgte denne metodikken er at målet er å ikke produsere et overskudd. Vi brukte da
et Kanban board (github issues boardet) for å visualisere hvor langt vi har kommet i prosjektet
og hvilke oppgaver som måtte gjøres videre.

Takket være arbeidsmetodikken så har vi implementert mye av backend-koden. Takket være Kanban så har vi valgt å
fokusere på fundamentene som har virket bra. På grunn av at Kanban også fokuserer på veldig konkrete issues så har det
vært få misforståelsder i kommunikasjonen.




Under vurdering vil det vektlegges at alle bidrar til kodebasen. Hvis det er stor forskjell i hvem som
committer, må dere legge ved en kort forklaring for hvorfor det er sånn. Husk å committe alt. (Også
designfiler)
Commit statiskken henter vi fra github.

Skillet i antall linjer skrevet kommer fra blant annet:
-kompleksitet i arbeidsoppgaver varierer veldig, så mange linjer reflekterer nødvendigvis ikke tiden som er brukt på
hver issue. Koden i ComponentFactory er for eksmepel mest gruntwork. Enkelte ting krever dessuten research og at man
leser seg opp på domenet ting omhandler. Koden på disse er ikke nødvendigvis ikke lang, men heller ommfattende.

-Dårlig bruk av git merge har resultert i at det ser ut som at
en person har skrevet kode som en annen har gjort. Dette er kommentert i koden.
Hvordan dette har skjedd er at to personer har jobbet med forskjelige issues i samme fil
som resulterer at en må pulle koden ned, sammtidig som den personen har skrevet egne commits. Dette resulterte i at git
gav oss en mergeconflict. Når personen som pullet skulle commite og pushe igjen ble de commitsene som en annen personen hadde
overskrevetet slik at de så ut som at det siste personen hadde skrevet alt. Vi har skjønnt at vi må bruke branches mer effektivt
og det har vi gjort nå.



Referat fra møter siden forrige leveranse skal legges ved (mange av punktene over er typisk ting som
havner i referat)..
Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.
