# Proyecto_PA

Proyecto final para la materia de Programación Avanzada.  
Equipo 7 - Axel, Alexis y yo.

---

Este proyecto consiste en analizar la afluencia por día del metro de la Ciudad de México.  
Desde el 1° de Enero del 2010 hasta el 31 de agosto del 2023.

La base de datos que será usada se obtuvo de la Secretaría de Movilidad (SEMOVI) y es un archivo CSV llamado [Afluencia Diara del Metro (Simple)](https://datos.cdmx.gob.mx/dataset/afluencia-diaria-del-metro-cdmx/resource/0e8ffe58-28bb-4dde-afcd-e5f5b4de4ccb "Link de descarga").  
El archivo consiste de 973,246 filas y 6 columnas, lo cual nos deja con 5,839,476 campos de información.  
Las columnas son:

---

|   fecha    | anio |  mes  |  linea  | estacion  | afluencia |
| :--------: | :--: | :---: | :-----: | :-------: | :-------: |
| 2010-01-31 | 2010 | Enero | Línea 4 | Consulado |    100    |

---

De igual manera se creó una base más grande para mostrar un poco más las ventajas del procesamiento paralelo y es la que está también incluida en el repositorio como _Datos10AniosMas.csv_

## Información genral de los campos

Línea 1 contiene las siguientes estaciones:

- Zaragoza
- Isabel la Católica
- Moctezuma
- Pino Suárez
- Gómez Farías
- Salto del Agua
- Balderas
- Tacubaya
- Observatorio
- Chapultepec
- Pantitlán
- Boulevard Puerto Aéreo
- Sevilla
- Balbuena
- Candelaria
- Merced
- Insurgentes
- Juanacatlán
- Cuauhtémoc
- San Lázaro

Línea 6 contiene las siguientes estaciones:

- Deportivo 18 de Marzo
- La Villa/Basílica
- El Rosario
- Ferrería/Arena Ciudad de México
- Instituto del Petróleo
- Martín Carrera
- Norte 45
- Vallejo
- Tezozómoc
- UAM-Azcapotzalco
- Lindavista

Línea 9 contiene las siguientes estaciones:

- Pantitlán
- Velódromo
- Chilpancingo
- Chabacano
- Centro Médico
- Patriotismo
- Puebla
- Ciudad Deportiva
- Mixiuhca
- Tacubaya
- Jamaica
- Lázaro Cárdenas

Línea 8 contiene las siguientes estaciones:

- Aculco
- Coyuya
- Apatlaco
- La Viga
- Chabacano
- San Juan de Letrán
- Salto del Agua
- Doctores
- Garibaldi/Lagunilla
- Bellas Artes
- Obrera
- Iztapalapa
- Cerro de la Estrella
- Atlalilco
- Escuadrón 201
- Iztacalco
- Santa Anita
- UAM-I
- Constitución de 1917

Línea 5 contiene las siguientes estaciones:

- Autobuses del Norte
- Misterios
- Hangares
- Pantitlán
- Eduardo Molina
- Aragón
- Oceanía
- Instituto del Petróleo
- Terminal Aérea
- Valle Gómez
- La Raza
- Politécnico
- Consulado

Línea 7 contiene las siguientes estaciones:

- Constituyentes
- Refinería
- Polanco
- Barranca del Muerto
- Tacubaya
- San Pedro de los Pinos
- Camarones
- San Antonio
- Mixcoac
- Tacuba
- Auditorio
- Aquiles Serdán
- San Joaquín
- El Rosario

Línea 3 contiene las siguientes estaciones:

- Etiopía/Plaza de la Transparencia
- División del Norte
- Zapata
- Indios Verdes
- Hidalgo
- Juárez
- Deportivo 18 de Marzo
- Guerrero
- Balderas
- Eugenia
- Copilco
- Centro Médico
- Universidad
- Coyoacán
- La Raza
- Viveros/Derechos Humanos
- Miguel Ángel de Quevedo
- Niños Héroes
- Hospital General
- Potrero
- Tlatelolco

Línea 4 contiene las siguientes estaciones:

- Canal del Norte
- Bondojito
- Santa Anita
- Fray Servando
- Candelaria
- Morelos
- Jamaica
- Martín Carrera
- Consulado
- Talismán

Línea 2 contiene las siguientes estaciones:

- Popotla
- General Anaya
- Revolución
- Colegio Militar
- Normal
- San Cosme
- Xola
- Portales
- Ermita
- San Antonio Abad
- Chabacano
- Viaducto
- Bellas Artes
- Cuitláhuac
- Cuatro Caminos
- Panteones
- Allende
- Zócalo/Tenochtitlan
- Nativitas
- Pino Suárez
- Hidalgo
- Villa de Cortés
- Tasqueña
- Tacuba

Línea B contiene las siguientes estaciones:

- Garibaldi/Lagunilla
- Buenavista
- Guerrero
- Oceanía
- Nezahualcóyotl
- Olímpica
- Ciudad Azteca
- Tepito
- Plaza Aragón
- Lagunilla
- Río de los Remedios
- Múzquiz
- Impulsora
- Ecatepec
- San Lázaro
- Romero Rubio
- Villa de Aragón
- Deportivo Oceanía
- Bosque de Aragón
- Morelos
- Ricardo Flores Magón

Línea 12 contiene las siguientes estaciones:

- Tlaltenco
- Insurgentes Sur
- Culhuacán
- Calle 11
- Lomas Estrella
- San Andrés Tomatlán
- Ermita
- Zapotitlán
- Nopalera
- Olivos
- Tezonco
- Periférico Oriente
- Tláhuac
- Hospital 20 de Noviembre
- Mixcoac
- Eje Central
- Zapata
- Parque de los Venados
- Atlalilco
- Mexicaltzingo

Línea A contiene las siguientes estaciones:

- Pantitlán
- Peñón Viejo
- Canal de San Juan
- Guelatao
- La Paz
- Acatitla
- Tepalcates
- Agrícola Oriental
- Santa Marta
- Los Reyes
