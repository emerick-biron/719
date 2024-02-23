import { atom } from 'recoil';

export const incubateEggIdsState = atom<number[]>({
  key: 'incubateEggIdsState',
  default: [],
});
