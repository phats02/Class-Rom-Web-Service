import { CLIENT_HOST } from "./constant";

export const generateLinkInvite = (invitationCode: string) => {
  return `${CLIENT_HOST}/classes/join/${invitationCode}`;
};
