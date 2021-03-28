#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct node{
  struct node* next;
  struct node* prev;
  int value;
  int key;
} node;

node* head1 = NULL;
node* head2 = NULL;
node* head3 = NULL;

void add(node** lista, int key, int val){
  node* ptr = (node*)malloc(sizeof(node));
  ptr->value = val;
  ptr->key = key;
  if(*lista == NULL){
    ptr->next = ptr;
    ptr->prev = ptr;
    *lista = ptr;
  }else{
    ptr->next = *lista;
    ptr->prev = (*lista)->prev;
    (*lista)->prev->next = ptr;
    (*lista)->prev = ptr;
    *lista = ptr;
  }
  
  
}


int find(node* lista, int key){
  node* tmp = lista;
  while(tmp->key != key)
    tmp = tmp->next;
  return tmp->value;
}

void display(node* lista){
  node* tmp = lista;
  do{
    printf("\n%d", tmp->value);
    tmp = tmp->next;
  }while(tmp != lista);
}

void displayRev(node* lista){
  node* tmp = lista;
   do{
    printf("\n%d", tmp->value);
    tmp = tmp->prev;
   }while(tmp != lista);
}

void merge(node* lista1, node* lista2){
  node* tmp = lista1->next;
  lista1->next = lista2;
  lista2->prev->next = tmp;
  tmp->prev = lista2->prev;
  lista2->prev = lista1;
  
}


int main(){
  srand(time(NULL));
  for(int i = 0; i<1000; i++){
     add(&head3, i, rand()%1000);
  }
  
  for(int i = 0; i<10; i++){
    add(&head1, i, i);
  }
  

  for(int i = 0; i<5; i++){
    add(&head2, i, i);
  }
  
  //display(head3);
  printf("\n");
  
  display(head1);
  printf("\n");
  
  display(head2);
  printf("\n");

  displayRev(head1);
  printf("\n");
  
  displayRev(head2);
  printf("\n");
  
  merge(head1, head2);
  display(head1);
  printf("\n");
  displayRev(head1);
  printf("\n");
  /*add(&head1, 15,15);
  display(head1);
  printf("\n");
  displayRev(head1);
  printf("\n");*/
  
  int random = rand()%1000;
  clock_t t = clock();
  for(int i = 0; i<1000; i++){
    find(head3, random);
  }
  t = clock() - t;
  double time_taken = ((double)t)/CLOCKS_PER_SEC;
  printf("\nTotal access time to the same element: %fs", time_taken);

  t = clock();
  for(int i = 0; i<1000; i++){
    find(head3, rand()%1000);
  }
  t = clock() - t;
  time_taken = ((double)t)/CLOCKS_PER_SEC;
  printf("\nTotal access time to a random element: %fs\n", time_taken);
  
}
