
import bpy

def add_cube(loc, scale = (1, 1, 1), size = 1):
    bpy.ops.mesh.primitive_cube_add(location=loc, scale=scale, size = size)
    

cube_positions = [
    (2, 2, 2),
    (1, 2, 2),
    (3, 2, 2),
    (2, 1, 2),
    (2, 3, 2),
    (2, 2, 1),
    (2, 2, 3),
    (2, 2, 4),
    (2, 2, 6),
    (1, 2, 5),
    (3, 2, 5),
    (2, 1, 5),
    (2, 3, 5)
]

for c in cube_positions:
    add_cube(c)
