import { IPolicy } from 'app/entities/policy/policy.model';

export interface IAgency {
  id?: number;
  name?: string | null;
  address?: string | null;
  branch?: string | null;
  brnachCode?: string | null;
  email?: string | null;
  companyTypeId?: number | null;
  imageUrl?: string | null;
  contactNo?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  policy?: IPolicy | null;
}

export class Agency implements IAgency {
  constructor(
    public id?: number,
    public name?: string | null,
    public address?: string | null,
    public branch?: string | null,
    public brnachCode?: string | null,
    public email?: string | null,
    public companyTypeId?: number | null,
    public imageUrl?: string | null,
    public contactNo?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public policy?: IPolicy | null
  ) {}
}

export function getAgencyIdentifier(agency: IAgency): number | undefined {
  return agency.id;
}
