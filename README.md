# Leitstellensystem - Projektarbeit OOP
### Projektarbeit Objektorientiertes Programmieren | 1. Semester - Digital Enterprise Management | Cosmo Stahn, Julian Schnabl

>**Kurzbeschreibung:**
>Bei dem Programm handelt es sich um ein Leitstellensystem (LSS) welches generell in Integrierten Leitstellen (ILS) der Feuerwehren und des Rettungsdienstes eingesetzt wird. Für diese Projektarbeit wurde das System im Vergleich zu einem reellen LSS vereinfacht.

## Gliederung
[1. Hintergrund (JU)](#1-hintergrund)<br>
[2. Einführung GUI-Fenster](#2-einführung-gui-fenster) // GUI Gesamt als Bild und Kurz Sinn erläutern (JU)<br>
[3. Erklärung einzelne Bereiche des GUI-Fensters](#3-erklärung-der-einzelnen-bereiche-des-gui-fensters) (JU)<br>
[4. Projektstruktur](#4-projektstruktur) (CO)<br>
[5. Funktionalitäten](#5-funktionalitäten) (CO)<br>
[6. JUnit Test](#6-junit-test) (JU)<br>
[7. Ausführen](#7-ausführen-des-programms) des Programms (JU)<br>
[8. Autoren](#8-autoren) (JU)<br>

## 1. Hintergrund
Über eine ILS wird die europaweite Notrufnummer 112 entgegengenommen. 
Welche Leitstelle damit erreicht wird, entscheidet sich durch die Mobilfunkmasten in denen das Handy eingewählt ist oder 
durch die Adresse bei der ein Festnetzanschluss registriert ist. Die verschiedenen Leitstellen werden nach Kommunen und 
den Landkreisen aufgeteilt. In Ulm ist die ILS in die Hauptfeuerwache der Feuerwehr Ulm in der Keplerstraße 38 in der 
Oststadt integriert. Diese ILS ist für das Stadtgebiet Ulm, sowie für den Alb-Donau Kreis zuständig. Für Neu-Ulm ist die 
ILS Donau-Iller zuständig, welche in Krumbach lokalisiert ist. Sollte man, z.B. weil man direkt an der Donau auf der 
Neu-Ulmer Seite steht in der ILS in Ulm rauskommen, wird der Anruf von dem Leitstellendisponenten direkt in die ILS 
Donau-Iller weitergeleitet. Die Notrufnummer 110 der Polizei wird nicht über die ILS sondern über das Führungs- und 
Lagezentrum (FLZ) des jewiligen Polizeipräsidiums bedient. 

Bei einem eingehenden Notruf wird der Anruf von einem Leitstellendisponenten entgegengenommen, dieser führt die bekannte 
Notrufabfrage mit den W-Fragen durch und gibt die angegeben Daten in ein LLS ein. Sobald alle grundlegenden Daten wie 
die Adresse und der Notfall klar sind, wählt der Disponent ein Alarmstichwort aus und nach einer festgelegten Alarm- und 
Ausrückeordnung (AAO) werden entsprechende Einsatzkräfte alarmiert. Oft geschiet die Alarmierung während des Telefonats 
und nach der Alarmierung werden weiterhin Informationen aufgenomern, diese werden weiterhin in das LSS eingegeben. Sind 
die Einsatzkräfte auf dem Weg, können so weitere Informationen über die Funkverbindung nachgereicht werden, ohne wertvolle 
Zeit zu verlieren. 

#### [↑ zurück zur Gliederung](#Gliederung)

## 2. Einführung GUI-Fenster
Das GUI-Fenster beinhaltet alle wichtigen Funktionen, die ein LSS können muss. Dazu gehört die Aufnahme von Daten aus
einem Notruf-Gespräch in ein Formular, die Alarmierung und die Verwaltung der laufenden Einsätze Mithilfe von Filter-Möglichkeiten
und der alphabetischen Sortierfunktion. Ebenfalls können die Einsätze in der Übersicht beendet werden.

![GUI_Fenster_komplett](Projektarbeit/src/main/resources/Bildschirmfoto_GUI_Fenster_komplett.png)

#### [↑ zurück zur Gliederung](#Gliederung)

## 3. Erklärung der einzelnen Bereiche des GUI-Fensters
### Das Eingabeformular
![Bildschirmfoto_GUI_Fenster_Formular.png](Projektarbeit/src/main/resources/Bildschirmfoto_GUI_Fenster_Formular.png)
`Vorgehensweise`
Wenn der Disponent einen Anrufer in der Leitung hat, kann hier schnell die Adresse und weitere Bemerkungen des Anrufers 
aufgenommen werden. Ebenfalls kann hinterlegt werden, ob sich noch Menschen in Gefahr befinden. Wenn die Situation ausreichend
geschildert wurde, wählt der Disponent das passende Stichwort und ob die Kräfte mit Signal (Sonder- und Wegerechte mit 
Martinshorn) anfahren sollen. Mit einem Klick auf *Alarmieren* werden die Einsatzkräfte dann alarmiert.

`Bedingungen`
Zu beachten ist, dass alle Felder bis auf das Feld *Bemerkung* ausgefüllt werden müssen. Ebenfalls muss die Postleitzahl 
eine 5-stellige Zahl sein. Ein Stichwort muss in jedem Fall gewählt werden. Nur wenn diese Bedingungen erfüllt sind,
können die Einsatzkräfte alarmiert werden. Wenn sich der Anruf z.B. als böswilliger Anruf herausstellt, können mit einem 
Klick auf *Eingabe löschen* die eingegebenen Daten aus dem Formular gelöscht werden. 
Hinweis: wird im Feld *Bemerkung* keine Änderung vorgenommen, erscheint in der Einsatzverwaltung der Vermerk: '- keine Angabe'

### Laufende Einsätze
![Bildschirmfoto_GUI_Fenster_JTable.png](Projektarbeit/src/main/resources/Bildschirmfoto_GUI_Fenster_JTable.png)
`Beschreibung`
Wenn der Disponent die Einsatzkräfte alarmiert, werden hier direkt die einzelnen Einsätze tabellarisch angezeigt. 
Hier werden die Informationen in die Spalten *Stichwort, Adresse, Bemerkung, Ort mit PLZ, Menschenleben in Gefahr (MiG) 
und Signalfahrt* angezeigt

### Einsatzverwaltung
![Bildschirmfoto_GUI_Fenster_Filtern_und_Sortieren.png](Projektarbeit/src/main/resources/Bildschirmfoto_GUI_Fenster_Filtern_und_Sortieren.png)<br>
`Vorgehensweise`
Wenn z.B. ein Funkspruch von einer Einsatzstelle die Leitstelle erreicht, kann über die Filterfunktion der richtige Einsatz,
gefunden werden. Hierfür muss eine Spalte nach der gefiltert werden soll ausgewählt werden, sowie ein passender Suchbegriff eingegeben werden.
Wenn es sich zum Beispiel um eine abschließende Lagemeldung handelt, kann der Einsatz mit einem Klick auf *Ausgewählte Einsätze 
beenden* beendet werden. Mit der Sortierfunktion kann man die Tabelle nach einer Spalte, die zuvor ausgewählt werden kann, 
alphabetisch sortiert werden.

`Bedingungen`
Die Buttons *Filter setzen* und *Alphabetisch sortieren* können nur verwendet werden, wenn eine Spalte aus der Combo-Box 
ausgewählt wurde, sowie beim Filtern ein Suchbegriff eingegeben wurde. Je nachdem ob gerade ein Filter gesetzt ist, werden über 
den unteren Button je nach Aufschrift entweder alle laufenden Einätze beendet oder nur die ausgewählten bzw. gefilterten 
Einsätze beendet


#### [↑ zurück zur Gliederung](#Gliederung)

## 4. Projektstruktur
Das Projekt besteht aus zwei zentralen Klassen. Die Klasse Einsatz dient als Bauplan für einzelne Einsätze und speichert 
alle einsatzrelevanten Informationen. Die Klasse Leitstellensystem fasst die grafische Benutzeroberfläche sowie die 
komplette Steuerungslogik des Systems zusammen und greift auf die erzeugten Einsatzobjekte zu.

### Klasse „Leitstellensystem“
Die Klasse Leitstellensystem erbt von JFrame und stellt somit das Hauptfenster der Anwendung dar. Sie ist für die gesamte 
Benutzerinteraktion, die Verarbeitung der Eingaben sowie die Verwaltung der Einsätze zuständig.

### Klasse „Einsatz“
Die Klasse Einsatz fungiert als Bauplan für einzelne Einsatzobjekte und beschreibt, welche Informationen ein Einsatz enthält.

### Zusammenspiel der Klassen
Die Klasse Einsatz wird innerhalb des Projekts ausschließlich als Datenobjekt verwendet. Sie enthält keine Logik zur 
Darstellung oder Bedienung, sondern stellt lediglich strukturierte Einsatzinformationen bereit.
Die Klasse Leitstellensystem erstellt neue Einsatzobjekte auf Basis der Benutzereingaben, speichert diese in einer Liste 
und stellt die enthaltenen Daten in tabellarischer Form dar. Änderungen an den Einsätzen, wie das Filtern, Sortieren 
oder Beenden, erfolgen zentral über die Klasse Leitstellensystem, greifen jedoch immer auf die zugrunde liegenden 
Einsatzobjekte zu. Durch dieses Zusammenspiel bleibt die Datenstruktur klar vom Ablauf und der Oberfläche getrennt, 
während gleichzeitig eine einfache und übersichtliche Verwaltung der Einsätze möglich ist.

#### [↑ zurück zur Gliederung](#Gliederung)

## 5. Funktionalitäten

## 6. JUnit Test

## 7. Ausführen des Programms
Das Programm wird durch das Starten der Klasse `Leitstellensystem` ausgeführt.  
Nach dem Start öffnet sich eine grafische Benutzeroberfläche, über die neue Einsätze erfasst und verwaltet werden können.  
Einsätze können über Eingabefelder angelegt und anschließend in einer Tabelle angezeigt werden.  
Zusätzlich besteht die Möglichkeit, Einsätze zu filtern, alphabetisch zu sortieren sowie alle oder gefilterte Einsätze zu beenden.  
Die Anwendung ist eine Desktop-Anwendung und benötigt keine zusätzliche Konfiguration oder externe Abhängigkeiten.

#### [↑ zurück zur Gliederung](#Gliederung)

## 8. Autoren 


>Cosmo Stahn *(Matrikel Nr: 392305)* <br>
>Julian Schnabl *(Matrikel Nr: 396345)*

`Modul: Objektorientierte Programmierung`
**Studiengang: Digital Enterprise Management (B.Sc.)<br>
Hochschule Neu-Ulm**<br>

#### [↑ zurück zur Gliederung](#Gliederung)
