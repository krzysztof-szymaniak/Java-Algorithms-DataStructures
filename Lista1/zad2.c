#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct node{
  struct node* next;
  int value;
  int key;
} node;

node* head1 = NULL;
node* head2 = NULL;
node* head3 = NULL;

void add(node** lista, int key, int val){
  node* ptr = (node*)malloc(sizeof(node));
  ptr->next = *lista;
  ptr->value = val;
  ptr->key = key;
  *lista = ptr;
  
}


int find(node* lista, int key){
  node* tmp = lista;
  while(tmp->key != key)
    tmp = tmp->next;
  return tmp->value;
}
void display(node* lista){
  node* tmp = lista;
  while(tmp != NULL){
    printf("\n%d", tmp->value);
    tmp = tmp->next;
  }
}
void merge(node* lista1, node* lista2){
  node* tmp = lista1;
  while(tmp->next != NULL){
    tmp = tmp->next;
  }
  tmp->next = lista2;
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
  
  merge(head1, head2);
  display(head1);
  printf("\n");
  
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
