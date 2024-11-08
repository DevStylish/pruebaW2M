import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StarshipFormComponent } from './starship-form.component';

describe('StarshipFormComponent', () => {
  let component: StarshipFormComponent;
  let fixture: ComponentFixture<StarshipFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StarshipFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StarshipFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
