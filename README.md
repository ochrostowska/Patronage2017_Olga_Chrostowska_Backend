# Patronage2017_Olga_Chrostowska_Backend
Zadanie 1 - grupa Backend

###REST API dla bazy filmów i aktorów

Actors
-------
```diff
> GET
```
>**`/actors`** - retrieve list of actors
>
> Response fields:
>- id
>- name 
>- surname

</n> 

```diff
> POST
```
>**`/actors`** - add new actor
>
>  Fields:
>- name 
>- surname
>
> Response fields
>- id
>- name
>- surname

</n> 

```diff
> DELETE
```
>**`/actors`** - delete all actors

</n> 


```diff
> GET
```
>**`/actors/{id}`** - find actor by id
>
> Response fields:
>- id
>- name 
>- surname

</n> 

```diff
> DELETE
```
>**`/actors/{id}`** - delete actor by id

</n> 

```diff
> PUT
```
>**`/actors/{id}`** - update actor by id
>
>  Fields:
>- name 
>- surname
>
> Response fields
>- id

</n> 


Movies
-------
```diff
> GET
```
>**`/movies`** - retrieve list of movies
>
> Response fields:
>- id
>- title 
>- year
>- genre
>- cast

</n> 

```diff
> POST
```
>**`/movies`** - add new movie
>
>  Fields:
>- title 
>- year
>- genre
>- cast
>
> Response fields
>- id
>- title 
>- year
>- genre
>- cast

</n> 

```diff
> DELETE
```
>**`/movies`** - delete all actors

</n> 

```diff
> GET
```
>**`/movies/{id}`** - find movie by id
>
> Response fields:
>- id
>- title 
>- year
>- genre
>- cast

</n> 

```diff
> DELETE
```
>**`/movies/{id}`** - delete movie by id

</n> 

```diff
> PUT
```
>**`/movies/{id}`** - update movie by id
>
>  Fields:
>- title 
>- year
>- genre
>- cast
>
> Response fields
>- id

</n> 

```diff
> POST
```
>**`/movies/{id}/actors`** - add actor to movie
>
> Response fields
>- actor id
>- name
>- surname

</n> 

```diff
> GET
```
>**`/movies/{id}/actors`** - retrieve movie cast
>
> Response fields
>- actor id
>- name
>- surname

</n> 



