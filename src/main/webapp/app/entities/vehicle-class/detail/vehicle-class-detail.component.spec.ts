import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VehicleClassDetailComponent } from './vehicle-class-detail.component';

describe('VehicleClass Management Detail Component', () => {
  let comp: VehicleClassDetailComponent;
  let fixture: ComponentFixture<VehicleClassDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VehicleClassDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ vehicleClass: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VehicleClassDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VehicleClassDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load vehicleClass on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.vehicleClass).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
