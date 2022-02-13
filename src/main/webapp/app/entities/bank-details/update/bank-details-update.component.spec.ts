import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BankDetailsService } from '../service/bank-details.service';
import { IBankDetails, BankDetails } from '../bank-details.model';

import { BankDetailsUpdateComponent } from './bank-details-update.component';

describe('BankDetails Management Update Component', () => {
  let comp: BankDetailsUpdateComponent;
  let fixture: ComponentFixture<BankDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bankDetailsService: BankDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BankDetailsUpdateComponent],
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
      .overrideTemplate(BankDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BankDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bankDetailsService = TestBed.inject(BankDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bankDetails: IBankDetails = { id: 456 };

      activatedRoute.data = of({ bankDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bankDetails));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BankDetails>>();
      const bankDetails = { id: 123 };
      jest.spyOn(bankDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bankDetailsService.update).toHaveBeenCalledWith(bankDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BankDetails>>();
      const bankDetails = new BankDetails();
      jest.spyOn(bankDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankDetails }));
      saveSubject.complete();

      // THEN
      expect(bankDetailsService.create).toHaveBeenCalledWith(bankDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BankDetails>>();
      const bankDetails = { id: 123 };
      jest.spyOn(bankDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bankDetailsService.update).toHaveBeenCalledWith(bankDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
