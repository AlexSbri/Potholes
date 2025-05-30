#include <netinet/in.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <pthread.h>
#include "../dependencies.h"
#include "../struct/Evento.h"
#define SOGLIA "3\n"
#define REQ_SOGLIA "soglia"
#define REQ_LISTA "lista"
#define REQ_EVENTO "evento"

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
int init_server();
void scriviEventoSuFile(Evento ev);
void inviaEvento(int socket,Evento ev);
void leggiEventoClient(int socket,char* eventoClient);
void inviaListaThread(int socket);
void leggiPosizioneClient(int socket,char* posizioneClient);
void leggiRichiesta(int socket, char* richiesta);
void leggiClient(int socket, char* client);
void svuotaBuffer(char *buffer);
void inviaRespOk(int socket);
void invia_soglia(int socket);
void init_address(struct sockaddr_in *address);
void *gestisci_richiesta(void *arg);
void deserialize_data(char data[][BUFSIZ],char *token);
void *insertEventoFile(void *arg);