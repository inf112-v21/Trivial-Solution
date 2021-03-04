#Deloppgave 1: Prosjekt og prosjektstruktur

###Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?
Rollene i teamet fungerer bra, vi trenger oppdattere rollene litt da, men vi beholder en lignende roller som forrige gang.

De rollene som er de samme som forrige gang:
-Teamleader --> Steinar
-Utvilkere --> Alle
-Referatansvarlig --> Vi rulere på denne, går på rundgang eller hvis noen har lyst den dagen.

###Trenger vi andre roller? Og hva innebærer de for oss
De nye rollene vi introduserte mellom Oblig1 og Oblig2:
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
Kundeansvarlig er ikke en rolle som vi har trengt per dags dato.
Vi har tross alt ingen kunder her, så vi så ikke på det som
nødvendig å ha en. 


###Team-messig erfaring og prosjektmetodikk
Vi synes at team-metodikken vi har valgt(Kanban) har funket veldig bra. Vi kunne ikke hatt Scrummøter hver eneste morgen
med timeplanene vår så vi er glade for at vi valgte Kanban. Dessuten så har alle i gruppen andre forpliktelser også, og 
takk vare Kanban så kan de kan ta færre issues den uken. Alle velger issuene de vil jobbe med selv på møtene og der
bestemmer vi hva vi skal fokusere på. Deretter er det enkelt å lage issuene (Kanban-kortene) som vi setter på
Kanban brettet.

###Hvordan er gruppedynamikken?
Vi synes at vi har en god gruppedynamikken. Vi har snakket med hverandre i løpet av prosjektet og spesielt i møtene.
Her har vi forklart planene våre. Alle har vært enige i hvilken rettning vi skal gå, så det har vært veldig få misforståelser.

###Hvordan fungerer kommunikasjonen for dere?
Kommunikasjonen fungerer veldig bra. Å kommunisere med hverandre er viktig og det har vi gjort.
Se svaret over.


###Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres.
Vi har valgt å bruke Kanban som hoved prosjektmetodikk og det har som sagt virket bra. Det vi likte med Kanban og grunnen
til at vi valgte denne metodikken er at målet er å ikke produsere et overskudd. Vi brukte da
et Kanban board (github issues boardet) for å visualisere hvor langt vi har kommet i prosjektet
og hvilke oppgaver som måtte gjøres videre. 

Vi lagde derimot kanskje litt for mange issues og gikk litt vekk fra MVP
kravene som ble fastsatt i Olbig1. Dette gjorde vi derimot fordi vi prioriterte andre ting. Vi ville ha et
fungerende spill ferdig i så stor grad som mulig med tanke på spill-logikk før vi begynnte med frontend og nettverk.
Tross at de siste to også er MVP krav så prioriterte vi de ikke like mye i starten. Det er kanskje noe vi burde gjort.

Men takket være arbeidsmetodikken så har vi implementert mye av backend-koden.På grunn av Kanban har vi også valgt å fokusere på veldig 
konkrete issues så har det vært få misforståelsder i kommunikasjonen.

Vi har sammarbeidet på en god måte som nevnt over ved å møte på gruppetimene og våre individuelle møter. Det har også som sagt
resultert i få misforståelser som nevt over, så på dette punktet synes vi at vi har gjort det veldig bra.




###Under vurdering vil det vektlegges at alle bidrar til kodebasen.

Forskjellen i antall linjer skrevet kommer fra blant annet:
-kompleksitet i arbeidsoppgaver varierer veldig, så mange linjer reflekterer nødvendigvis ikke tiden som er brukt på
hver issue. Koden i ComponentFactory er for eksmepel mest gruntwork. Enkelte ting krever dessuten research og at man
leser seg opp på domenet ting omhandler. Koden på disse er ikke nødvendigvis ikke lang, men heller ommfattende.

-Dårlig bruk av git merge har resultert i at det ser ut som at
en person har skrevet kode som en annen har gjort. Dette er kommentert i commitsene.
Hvordan dette har skjedd er at to personer har jobbet med forskjelige issues i samme fil
som resulterer at en må pulle koden ned, sammtidig som den personen har skrevet egne commits. Dette resulterte i at git
gav oss en mergeconflict når vi brukte git add . (punktum = add alt). Når personen som pullet skulle commite og pushe igjen ble de commitsene som en annen personen hadde
overskrevetet slik at de så ut som at det siste personen hadde skrevet alt. Vi har skjønnt at vi må bruke branches mer effektivt
og det har vi gjort under arbeidet i for denne Obligen. Dette har Dusan hatt problemer med og han har litt mindre faktiskt
skrevne linjer enn det som står oppgitt i git hub statiskken. 

Ilyas og Samuel har færre linjer skrevet siden de har holdt på med spesifikke GUI-relaterte issues, som har krevd mer reasearch på
dette stadiet av prosjektet så derfor har de ikke commitetet like mye. 

Steinar har fokusert mest på backenden av proskjektet på dette tidspunktet og har dermed skrevet flere linjer. Han begynner på
GUI-delen snart. Det er et par ting igjen på Backend, men vi er fornøyde med hvor langt vi har komt her generelt nå.

Sander fokuserte også stort sett på GUI-delene av prosjektet (spesifikkt kort implementasjonen) og har brukt en stor del
av tiden på å researche dette.

Dusan har gjort en del av backend og har vært ansvarlig for at spilleren kan vinne etter å hente flaggene i det siste. Det
kravet er nå ferdig så han har begynte med å reaserche hvordan vi skal sette opp nettverket.



Referat fra møter siden forrige leveranse skal legges ved (mange av punktene over er typisk ting som
havner i referat)..
Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.


#Deloppgave 2 - Krav

