import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BankDetailsDetailComponent } from './bank-details-detail.component';

describe('BankDetails Management Detail Component', () => {
  let comp: BankDetailsDetailComponent;
  let fixture: ComponentFixture<BankDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BankDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bankDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BankDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BankDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bankDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bankDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
