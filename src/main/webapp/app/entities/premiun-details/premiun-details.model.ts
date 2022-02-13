import { IPolicy } from 'app/entities/policy/policy.model';

export interface IPremiunDetails {
  id?: number;
  premium?: number | null;
  otherLoading?: number | null;
  otherDiscount?: number | null;
  addOnPremium?: number | null;
  liabilityPremium?: number | null;
  odPremium?: number | null;
  personalAccidentDiscount?: boolean | null;
  personalAccident?: number | null;
  grossPremium?: number | null;
  gst?: number | null;
  netPremium?: number | null;
  lastModified?: string;
  lastModifiedBy?: string;
  policy?: IPolicy | null;
}

export class PremiunDetails implements IPremiunDetails {
  constructor(
    public id?: number,
    public premium?: number | null,
    public otherLoading?: number | null,
    public otherDiscount?: number | null,
    public addOnPremium?: number | null,
    public liabilityPremium?: number | null,
    public odPremium?: number | null,
    public personalAccidentDiscount?: boolean | null,
    public personalAccident?: number | null,
    public grossPremium?: number | null,
    public gst?: number | null,
    public netPremium?: number | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public policy?: IPolicy | null
  ) {
    this.personalAccidentDiscount = this.personalAccidentDiscount ?? false;
  }
}

export function getPremiunDetailsIdentifier(premiunDetails: IPremiunDetails): number | undefined {
  return premiunDetails.id;
}
