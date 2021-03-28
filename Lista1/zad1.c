#include <stdio.h>
#include <stdlib.h>

typedef struct node{
  struct node* next;
  int value;
} node;

node* head = NULL;
node* tail = NULL;

void add(int val){
  node* ptr = (node*)malloc(sizeof(node));
  ptr->next = NULL;
  ptr->value = val;
  if (head == NULL){
    head = ptr;
    tail = head;
  }
  else{
    tail->next = ptr;
    tail = ptr;
  }
}

void pop(){
  node* newhead = head->next;
  free(head);
  head = newhead;
}
void display(){
  node* tmp = head;
  while(tmp != NULL){
    printf("\n%d", tmp->value);
    tmp = tmp->next;
  }
}


int main(){
  for(int i = 0; i<10; i++){
    add(i);
  }
  display();
  pop();
  add(20);
  printf("\n");
  display();
  printf("\n");
}
