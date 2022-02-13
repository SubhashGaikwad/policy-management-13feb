import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VehicleDetailsDetailComponent } from './vehicle-details-detail.component';

describe('VehicleDetails Management Detail Component', () => {
  let comp: VehicleDetailsDetailComponent;
  let fixture: ComponentFixture<VehicleDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VehicleDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ vehicleDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VehicleDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VehicleDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load vehicleDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.vehicleDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
