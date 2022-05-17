# CovidStats
Applicazione android che effettua due distinte richieste ad un open data aggiornato regolarmente ogni giorno (circa verso le ore 18:00) il numero di contagi COVID-19.
RICHIESTA 1: numero di contagi di una certa provincia da inizio pandemia fino al giorno odienro.
RICHIESTA 2: numero di contagi da inizio pandemia fino al giorno odierno di tutte le province italiane.

I dati vengono salvati su un file nel sistema in modo tale che si evita di rifare la richiesta alla repository di github utilizzata:
https://github.com/pcm-dpc/COVID-19

Quando si cambia activity la prima volta questi dati vengono salvati in un file di sistema chiamato config.csv.

Viene poi utilizzato un intent che invia un bundle contente una stringa per verificare il corretto salvataggio.

La prima volta che l'applicazione viene avviata viene in ogni caso fatta la richiesta alla repository dove verrà scaricato il file (grande circa 10MB).

Vengono utilizzati degli spinner dinamici e un datePickerDialog per avere un look e feel più interattivo, allo stesso tempo limitando i possibili errori di input dell'utente nel caso in cui gli si venga lasciata completa liberta con dei semplici EditText
