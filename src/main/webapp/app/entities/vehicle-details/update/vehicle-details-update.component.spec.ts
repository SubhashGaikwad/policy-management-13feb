import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VehicleDetailsService } from '../service/vehicle-details.service';
import { IVehicleDetails, VehicleDetails } from '../vehicle-details.model';

import { VehicleDetailsUpdateComponent } from './vehicle-details-update.component';

describe('VehicleDetails Management Update Component', () => {
  let comp: VehicleDetailsUpdateComponent;
  let fixture: ComponentFixture<VehicleDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vehicleDetailsService: VehicleDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VehicleDetailsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VehicleDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VehicleDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vehicleDetailsService = TestBed.inject(VehicleDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const vehicleDetails: IVehicleDetails = { id: 456 };

      activatedRoute.data = of({ vehicleDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(vehicleDetails));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VehicleDetails>>();
      const vehicleDetails = { id: 123 };
      jest.spyOn(vehicleDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicleDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicleDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(vehicleDetailsService.update).toHaveBeenCalledWith(vehicleDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VehicleDetails>>();
      const vehicleDetails = new VehicleDetails();
      jest.spyOn(vehicleDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicleDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicleDetails }));
      saveSubject.complete();

      // THEN
      expect(vehicleDetailsService.create).toHaveBeenCalledWith(vehicleDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VehicleDetails>>();
      const vehicleDetails = { id: 123 };
      jest.spyOn(vehicleDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicleDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vehicleDetailsService.update).toHaveBeenCalledWith(vehicleDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
