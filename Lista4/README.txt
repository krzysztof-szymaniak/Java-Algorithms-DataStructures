Kompilacja:
javac *.java

Uruchomienie:
java Main --type bst/rbt/hmap < plik_wejsciowy

W celu przeprowadzania testow dodalem dwa polecenia, ktore mozna wprowadzic do programu:
deleteTest file_name , zachowuje sie ono odwrotnie niz polecenie load. Usuwa ze struktury wszystkie slowa znajdujace w tekscie zrodlowym.
findTest file_name stara sie odnalezc w strukturze wszystkie slowa znajdujace sie w tekscie zrodlowym.
Polecenia load, deleteTest, findTest po zakonczeniu wypisuja statystyki z nimi zwiazane.

Wyniki testow znajduja sie w plikach nt_test.txt oraz wyniki.txt