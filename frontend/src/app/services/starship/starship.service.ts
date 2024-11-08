import { Injectable, computed, signal } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, timeout, map, tap } from 'rxjs/operators';

interface Starship {
  name: string;
  model: string;
  starshipClass: string;
  manufacturer: string;
  costInCredits: number;
  length: number;
  crew: number;
  passengers: number;
  maxAtmospheringSpeed: string;
  MGLT: string;
  cargoCapacity: number;
  consumables: string;
  films: string[];
  pilots: string[];
  starshipImage: string;
}

interface ApiResponse<T> {
  data?: T;
  message?: string;
  error?: string;
}

@Injectable({
  providedIn: 'root',
})
export class StarshipService {
  private readonly API_URL = 'http://localhost:8080/starship';

  private starshipsSignal = signal<any[]>([]);
  private loadingSignal = signal<boolean>(false);
  errorSignal = signal<string | null>(null);

  readonly starships = computed(() => this.starshipsSignal());
  readonly loading = computed(() => this.loadingSignal());
  readonly error = computed(() => this.errorSignal());

  constructor(private http: HttpClient) {}

  /**
   * Obtiene todas las naves espaciales
   */
  findAll(): void {
    this.loadingSignal.set(true);
    this.errorSignal.set(null);

    this.http
      .get<any[]>(`${this.API_URL}/`)
      .pipe(
        map((response) => {
          // Si la respuesta es un string (mensaje de error), lanzar error
          if (typeof response === 'string') {
            throw new Error(response);
          }
          return response;
        }),
        catchError(this.handleError.bind(this))
      )
      .subscribe({
        next: (data: any) => {
          this.starshipsSignal.set(Array.isArray(data) ? data : []);
          this.loadingSignal.set(false);
        },
        error: (error) => {
          this.errorSignal.set(error.message);
          this.loadingSignal.set(false);
        },
      });
  }

  // Método para la búsqueda en la lista (usando signals)
  findByIdForList(id: string | number): void {
    this.loadingSignal.set(true);
    this.errorSignal.set(null);

    this.getStarshipById(id).subscribe({
      next: (data) => {
        if (data) {
          this.starshipsSignal.set([data]);
        } else {
          this.starshipsSignal.set([]);
          this.errorSignal.set('No se encontró la nave espacial');
        }
        this.loadingSignal.set(false);
      },
      error: (error) => {
        this.errorSignal.set(error.message);
        this.loadingSignal.set(false);
        this.starshipsSignal.set([]);
      },
    });
  }

  /**
   * Método para obtener la nave usada en el formulario (devolviendo Observable)
   */
  findById(id: number): Observable<any> {
    return this.getStarshipById(id);
  }

  /**
   * Método privado para hacer la petición HTTP de la nave mediante el id
   */
  private getStarshipById(id: string | number): Observable<any> {
    const searchId = id.toString();
    return this.http
      .get<any>(`${this.API_URL}/id/${searchId}`)
      .pipe(catchError(this.handleError));
  }

  /**
   * Busca naves espaciales por nombre
   */
  findByName(name: string): void {
    this.loadingSignal.set(true);
    this.errorSignal.set(null);

    this.http
      .get<any[]>(`${this.API_URL}/name/${name}`)
      .pipe(
        map((response) => {
          if (typeof response === 'string') {
            throw new Error(response);
          }
          return response;
        }),
        catchError(this.handleError.bind(this))
      )
      .subscribe({
        next: (data: any) => {
          this.starshipsSignal.set(Array.isArray(data) ? data : []);
          this.loadingSignal.set(false);
        },
        error: (error) => {
          this.errorSignal.set(error.message);
          this.loadingSignal.set(false);
        },
      });
  }

  /**
   * Crea una nueva nave espacial
   */
  createStarship(starship: any): Observable<any> {
    return this.http.post(`${this.API_URL}/`, starship).pipe(
      map((response) => {
        if (typeof response === 'string') {
          throw new Error(response);
        }
        return response;
      }),
      catchError(this.handleError.bind(this))
    );
  }

  /**
   * Actualiza una nave espacial existente
   */
  updateStarship(starship: any): Observable<any> {
    console.log(starship);
    return this.http.patch(`${this.API_URL}/`, starship).pipe(
      map((response) => {
        if (typeof response === 'string') {
          throw new Error(response);
        }
        return response;
      }),
      catchError(this.handleError.bind(this))
    );
  }

  /**
   * Elimina una nave espacial por su ID
   */
  removeById(id: number): Observable<ApiResponse<void>> {
    this.loadingSignal.set(true);
    this.errorSignal.set(null);

    return this.http.delete<ApiResponse<void>>(`${this.API_URL}/id/${id}`).pipe(
      map((response) => {
        // Si la respuesta es un string, convertirlo a objeto ApiResponse
        if (typeof response === 'string') {
          return { message: response };
        }
        return response;
      }),
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'Ha ocurrido un error desconocido';

        if (error.error instanceof ErrorEvent) {
          // Error del lado del cliente
          errorMessage = `Error del cliente: ${error.error.message}`;
        } else if (error.status === 0) {
          // Error de conexión
          errorMessage =
            'No se puede conectar con el servidor. Por favor, verifica tu conexión';
        } else if (typeof error.error === 'string') {
          // Error como string
          errorMessage = error.error;
        } else if (error.error?.message) {
          // Error como objeto con mensaje
          errorMessage = error.error.message;
        }

        this.errorSignal.set(errorMessage);
        this.loadingSignal.set(false);
        return throwError(() => new Error(errorMessage));
      }),
      map((response) => {
        this.loadingSignal.set(false);
        return response;
      })
    );
  }

  /**
   * Función para manejar errores
   */
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Ha ocurrido un error desconocido';

    if (error.error instanceof ErrorEvent) {
      // Error del cliente
      errorMessage = `Error: ${error.error.message}`;
    } else if (typeof error.error === 'string') {
      // Error del servidor como string
      errorMessage = error.error;
    } else if (error.error?.message) {
      // Error del servidor como objeto
      errorMessage = error.error.message;
    } else if (typeof error === 'string') {
      // Error como string directo
      errorMessage = error;
    }

    console.error('Error en la petición:', errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
