import { IPolicy } from 'app/entities/policy/policy.model';

export interface IBankDetails {
  id?: number;
  name?: string | null;
  branch?: string | null;
  branchCode?: string | null;
  city?: number | null;
  contactNo?: number | null;
  ifcCode?: string | null;
  account?: string | null;
  accountType?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  policy?: IPolicy | null;
}

export class BankDetails implements IBankDetails {
  constructor(
    public id?: number,
    public name?: string | null,
    public branch?: string | null,
    public branchCode?: string | null,
    public city?: number | null,
    public contactNo?: number | null,
    public ifcCode?: string | null,
    public account?: string | null,
    public accountType?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public policy?: IPolicy | null
  ) {}
}

export function getBankDetailsIdentifier(bankDetails: IBankDetails): number | undefined {
  return bankDetails.id;
}
