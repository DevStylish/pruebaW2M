import { Component, signal, computed, inject } from '@angular/core';
import { Router } from '@angular/router';
import { StarshipService } from '../services/starship/starship.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-starship',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './starship.component.html',
  styleUrl: './starship.component.css',
})
export class StarshipComponent {
  // Signals
  searchId = signal<string>('');
  searchName = signal<string>('');
  page = signal<number>(1); // Variable de las paginas
  pageSize = signal<number>(10); // Variable para indicar tamaño de paginado

  // Variable para calcular cuantas paginas son necesarias mostrar en base al numero de naves obtenidas y el limite del paginado
  totalPages = computed(() =>
    Math.ceil(this.starshipService.starships().length / this.pageSize())
  );

  // Variable para almacenar el número de páginas en un array
  totalPagesArray = computed(() =>
    Array.from({ length: this.totalPages() }, (_, i) => i + 1)
  );

  // Codigo para crear un array con las naves obtenidas y paginarlas
  paginatedStarships = computed(() => {
    const start = (this.page() - 1) * this.pageSize();
    const end = start + this.pageSize();
    return this.starshipService.starships().slice(start, end);
  });

  constructor(public starshipService: StarshipService, private router: Router) {
    this.loadStarships();
  }

  // Mostrar todas las naves
  loadStarships(): void {
    this.starshipService.findAll();
  }

  // Buscar por id en el listado
  findById(): void {
    if (this.searchId()) {
      this.starshipService.findByIdForList(parseInt(this.searchId()));
    } else {
      this.starshipService.findAll();
    }
  }

  // Buscar por nombre
  findByName(): void {
    if (this.searchName()) {
      this.starshipService.findByName(this.searchName());
    } else {
      this.starshipService.findAll();
    }
  }

  // Eliminar nave
  deleteStarship(id: number): void {
    this.starshipService.removeById(id).subscribe({
      next: (response) => {
        console.log('Nave eliminada exitosamente:', response.message);
        // Actualizar la lista de naves después del borrado exitoso
        this.starshipService.findAll();
      },
      error: (error) => {
        console.error('Error al eliminar la nave:', error.message);
        // El error ya se maneja en el servicio y se muestra a través del errorSignal
      },
    });
  }

  // Crear nave
  goToCreateStarship(): void {
    this.router.navigate(['/starships/create']);
  }

  // Editar nave
  goToEditStarship(starship: any): void {
    this.router.navigate(['/starships/edit', starship.id]);
  }

  // Función para cambiar de página
  changePage(newPage: number): void {
    if (newPage >= 1 && newPage <= this.totalPages()) {
      this.page.set(newPage);
    }
  }
}
