import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PremiunDetailsService } from '../service/premiun-details.service';
import { IPremiunDetails, PremiunDetails } from '../premiun-details.model';

import { PremiunDetailsUpdateComponent } from './premiun-details-update.component';

describe('PremiunDetails Management Update Component', () => {
  let comp: PremiunDetailsUpdateComponent;
  let fixture: ComponentFixture<PremiunDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let premiunDetailsService: PremiunDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PremiunDetailsUpdateComponent],
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
      .overrideTemplate(PremiunDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PremiunDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    premiunDetailsService = TestBed.inject(PremiunDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const premiunDetails: IPremiunDetails = { id: 456 };

      activatedRoute.data = of({ premiunDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(premiunDetails));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PremiunDetails>>();
      const premiunDetails = { id: 123 };
      jest.spyOn(premiunDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ premiunDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: premiunDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(premiunDetailsService.update).toHaveBeenCalledWith(premiunDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PremiunDetails>>();
      const premiunDetails = new PremiunDetails();
      jest.spyOn(premiunDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ premiunDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: premiunDetails }));
      saveSubject.complete();

      // THEN
      expect(premiunDetailsService.create).toHaveBeenCalledWith(premiunDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PremiunDetails>>();
      const premiunDetails = { id: 123 };
      jest.spyOn(premiunDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ premiunDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(premiunDetailsService.update).toHaveBeenCalledWith(premiunDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
