# Prova Finale Ingegneria del Software - AA 2022/23
![alt text](https://github.com/ArimondoScrivano/ing-sw-2023-scrivano-vercelloni-pavesi-sartini/assets/126161280/88a84b55-0265-4f46-b7f6-b2b309d1a73f)

## Overview
Il programma è un'implementazione digitale del gioco da tavolo [**My Shelfie**] prodotto da [_Cranio Creations_].

Il progetto è stato sviluppato per la prova finale del corso Ingegneria del Software 2022/2023, Politecnico di Milano.

### Game plot
Il gioco consiste nel riempire la propria libreria con svariati oggetti, disponendoli in modo da ottenere il maggior numero di punti possibili.

La partita ha inizio posizionando le tessere oggetto sulla plancia soggiorno, in quantità variabile a seconda del numero di giocatori.
Durante il proprio turno, i giocatori potranno scegliere se prendere 1, 2 o 3 tessere oggetto adiacenti tra loro e con almeno un lato libero dalla plancia, per poi posizionarle in un’unica colonna all’interno della loro libreria.

Gioca un ruolo fondamentale il modo in cui il giocatore sceglie di collocare le tessere all'interno della libreria, poichè consente di guadagnare punti tramite il conseguimento dell'obiettivo personale e degli obiettivi comuni. Le carte obiettivo richiedono infatti di soddisfare una specifica disposizione di tessere, basandosi sui diversi colori.

Il primo giocatore a completare la libreria guadagna un punto aggiuntivo ed innesca la fine della partita, al termine della quale avverrà il conteggio dei punti che proclamerà il vincitore.

### Link al regolamento ufficiale
[Regole complete del gioco](https://www.craniocreations.it/storage/media/product_downloads/79/1010/Rulebook_ITA_My-Shelfie.pdf)


## Struttura
**Pattern architetturale:** MVC (Model-View-Controller), sfruttato per separare la logica di presentazione dei dati dalla logica di business dell'applicazione.

**Network:** si basa sull'implementazione di entrambi i protocolli di rete RMI e socket (TCP-IP).
- __Server:__ unico, in grado di supportare più partite simultaneamente e multipli client (uno per ogni giocatore di ciascuna partita).
- __Client:__ ogni client sfrutta RMI o socket per connettersi al server di gioco. La scelta del tipo di protocollo da utilizzare è resa disponibile all'utente in fase di avvio del programma.

**UI:** sono implementate due possibili User Interface anch'esse selezionabili dall'utente in fase di avvio.
- __CLI:__ linea di comando
- __GUI:__ interfaccia utente grafica


## Documentazione

### UML
Diagrammi delle classi creati in fase di progettazione del programma: [UML](https://github.com/ArimondoScrivano/ing-sw-2023-scrivano-vercelloni-pavesi-sartini/tree/main/deliveries/UML_alto_livello)

### JavaDoc
Documentazione Java che descrive classi e metodi utilizzati: [JavaDoc](https://github.com/ArimondoScrivano/ing-sw-2023-scrivano-vercelloni-pavesi-sartini/tree/main/deliveries/javaDoc)

### Testing
Resoconto sulla copertura degli unit test effettuati in fase di sviluppo del programma: [test](https://github.com/ArimondoScrivano/ing-sw-2023-scrivano-vercelloni-pavesi-sartini/tree/main/src/test)

### Librerie e Plugins
|                 | Utilizzo |
| --------------- | ----------- |
|[__Maven__](https://maven.apache.org/)| build automation del progetto |
|[__JSON__](https://json.org/json-it.html)| formato testuale per la strutturazione dati |
|[__JavaSwing__](https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html)| package per lo sviluppo della GUI|
|[__JUnit5__](https://junit.org/junit5/)| framework per unit testing |


## Funzionalità implementate
- Regole complete
- Versione CLI
- Versione GUI
- RMI
- Socket
- Funzionalità avanzata:
    - __Partite multiple:__ il server supporta più partite contemporaneamente.


## Compilazione ed Esecuzione

Client
Il client può essere eseguito utilzzando la modalità CLI e la modalità GUI
E' necessario eseguire il client con un terminale che supporti UTF-8 e gli ANSI escape codes per una migliore esperienza nell'eventuale scelta della Cli.
Il comando per eseguire il client in modalità CLI/GUI è il seguente:

java -jar Client.jar

Server
Il comando per eseguire il Server è il seguente:

java -jar Server.jar

 
## Software utilizzati
* [Draw.io - Diagrams.net](https://app.diagrams.net/): UML diagrams.

* [IntelliJ](https://www.jetbrains.com/idea/): IDE di sviluppo principale.


## Licenze
[**My Shelfie**] è una proprietà di [_Cranio Creations_].

[_Cranio Creations_]: https://www.craniocreations.it/
[**My Shelfie**]: https://www.craniocreations.it/prodotto/my-shelfie/


## Team members
* [__Arimondo Scrivano__](https://github.com/ArimondoScrivano)
* [__Lorenzo Vercelloni__](https://github.com/LorenzoVercelloni)   
* [__Maria Rita Sartini__](https://github.com/MariaRitaSartini)
* [__Pietro Pavesi__](https://github.com/pvsptr)
