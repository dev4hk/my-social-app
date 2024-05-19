import { API_BASE_URL } from "../config/api";

export const generateMediaURL = (fileName, filePath) => {
  return `${API_BASE_URL}/load-media?fileName=${fileName}&filePath=${filePath}`;
};
