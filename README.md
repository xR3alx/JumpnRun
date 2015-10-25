# JumpnRun


# Tiled Map

**Layern der Ebenen**

Die Reihenfolge in Tiled entspricht nicht der Reihenfolge wie sie im Spiel gezeichnet werden.
Damit eine Ebene gezeichnet muss sie einem Layer hinzugefügt werden. Dazu wählt man die Ebene in Tiled aus und fügt die Eigenschaft "layer" hinzu. Als Wert kann dann "foreground", "entities"(Entities werden autmatisch zu dieser hinzugefügt), "midground" oder "background" sein. Wenn man einen neuen Layer erstellen muss, dann muss man neben "layer" und als Wert den Namen des neuen Layer auch noch die Eigenschaft "layer_priority" und einen Ganzzahlwert hinzufügen.
Der Layer "foreground" hat die Priorität 2, "entities" hat 3, "midground" hat 4 und "background" hat 5.
Es ist möglich als "layer_priority" z.B. 2 zu wählen, dann bekommt der neu erstellte Layer die Priorität 2 und alle anderen rutschen eine Priorität nach unten.


