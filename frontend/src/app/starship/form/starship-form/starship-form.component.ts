import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { StarshipService } from '../../../services/starship/starship.service';

@Component({
  selector: 'app-starship-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './starship-form.component.html',
  styleUrl: './starship-form.component.css',
})
export class StarshipFormComponent implements OnInit {
  // Variables
  starshipForm: FormGroup;
  isEditMode = signal<boolean>(false);
  private starshipId: number | null = null;

  constructor(
    private fb: FormBuilder,
    public starshipService: StarshipService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.starshipForm = this.fb.group({
      name: ['', [Validators.required]],
      model: ['', [Validators.required]],
      starshipClass: [''],
      manufacturer: ['', [Validators.required]],
      costInCredits: [null, [Validators.min(0)]],
      length: [null, [Validators.min(0)]],
      crew: [null, [Validators.min(0)]],
      passengers: [null, [Validators.min(0)]],
      maxAtmospheringSpeed: [''],
      MGLT: [''],
      cargoCapacity: [null, [Validators.min(0)]],
      consumables: [''],
      films: [[]],
      pilots: [[]],
      starshipImage: [''],
    });
  }

  // Al cargar, si obtiene id, se abre en modo edición
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode.set(true);
      this.starshipId = parseInt(id);
      this.loadStarship(this.starshipId);
    }
  }

  // Función para cargar los datos en modo edición
  loadStarship(id: number): void {
    this.starshipService.findById(id).subscribe({
      next: (starship) => {
        this.starshipForm.patchValue(starship);
      },
      error: (error) => {
        console.error('Error al cargar la nave:', error);
        this.starshipService.errorSignal.set(error.message);
      },
    });
  }

  // Función para comprobar campo del formulario está relleno
  isFieldInvalid(fieldName: string): boolean {
    const field = this.starshipForm.get(fieldName);
    return field ? field.invalid && (field.dirty || field.touched) : false;
  }

  // Función para enviar el formulario
  onSubmit(): void {
    if (this.starshipForm.valid) {
      const starship = this.starshipForm.value;

      // Si estamos editando se actualiza, en caso contrario, creamos la nave
      if (this.isEditMode()) {
        // Incluimos el ID solo cuando estamos editando
        const starshipWithId = {
          ...starship,
          id: this.starshipId,
        };
        this.starshipService.updateStarship(starshipWithId).subscribe({
          next: () => {
            console.log('Nave actualizada exitosamente');
            this.goBack();
          },
          error: (error) => {
            console.error('Error al actualizar la nave:', error);
          },
        });
      } else {
        this.starshipService.createStarship(starship).subscribe({
          next: () => {
            console.log('Nave creada exitosamente');
            this.goBack();
          },
          error: (error) => {
            console.error('Error al crear la nave:', error);
          },
        });
      }
    }

    this.starshipForm.clearValidators();
  }

  goBack(): void {
    this.router.navigate(['/starships']);
  }
}
