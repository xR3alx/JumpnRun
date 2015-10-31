# JumpnRun

### Tiled Map

**Ambient Light**

Unter dem Menüpunkt "Karte" und dort "Karteneigenschaften" muss die Eigenschaft "rgba" hinzugefügt werden.
Als Wert muss dann eine Zahl zwischen 0 und 1 (0.01, 0.2 etc.) erscheinen in folgendem Schema: R,G,B,A
Dabei werden die Buchstaben mit dem entsprechenden Wert ersetzt.
* R - Rot
* G - Grün
* B - Blau
* A - Transparenz (Alpha)

**Layern der Ebenen**

Die Reihenfolge in Tiled entspricht nicht der Reihenfolge wie sie im Spiel gezeichnet werden.
Damit eine Ebene gezeichnet muss sie einem Layer hinzugefügt werden. Dazu wählt man die Ebene in Tiled aus und fügt die Eigenschaft "layer" hinzu. Als Wert kann dann "foreground", "entities"(Entities werden autmatisch zu dieser hinzugefügt), "midground" oder "background" sein. Wenn man einen neuen Layer erstellen muss, dann muss man neben "layer" und als Wert den Namen des neuen Layer auch noch die Eigenschaft "layer_priority" und einen Ganzzahlwert hinzufügen.
Es ist möglich als "layer_priority" z.B. 2 zu wählen, dann bekommt der neu erstellte Layer die Priorität 2 und alle anderen rutschen eine Priorität nach unten.

*Prioritäten*
* foreground - 2
* entities - 3
* midground - 4
* background - 5

**Kollision**

Um Kollision hinzuzufügen muss in Tiled die Ebene "collision" erstellt werden. Dort können dann Rechtecke, Ellipsen, Polygone und Polylinien hinzugefügt werden. Bei jedem Objekt muss als Typ "object" stehen.

**Entities**

Um Entities(Spieler, Gegner, Sammelbares) hinzuzufügen muss in Tiled die Ebene "entities" erstellt werden. Dort müssen(!) dann Rechtecke hinzugefügt werden. Als Typ setzt man dann den Entitytyp.
* Spieler(Player): 0
