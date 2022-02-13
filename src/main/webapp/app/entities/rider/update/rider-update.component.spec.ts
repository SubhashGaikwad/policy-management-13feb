import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RiderService } from '../service/rider.service';
import { IRider, Rider } from '../rider.model';

import { RiderUpdateComponent } from './rider-update.component';

describe('Rider Management Update Component', () => {
  let comp: RiderUpdateComponent;
  let fixture: ComponentFixture<RiderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let riderService: RiderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RiderUpdateComponent],
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
      .overrideTemplate(RiderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RiderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    riderService = TestBed.inject(RiderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const rider: IRider = { id: 456 };

      activatedRoute.data = of({ rider });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(rider));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Rider>>();
      const rider = { id: 123 };
      jest.spyOn(riderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rider });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rider }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(riderService.update).toHaveBeenCalledWith(rider);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Rider>>();
      const rider = new Rider();
      jest.spyOn(riderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rider });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rider }));
      saveSubject.complete();

      // THEN
      expect(riderService.create).toHaveBeenCalledWith(rider);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Rider>>();
      const rider = { id: 123 };
      jest.spyOn(riderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rider });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(riderService.update).toHaveBeenCalledWith(rider);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
