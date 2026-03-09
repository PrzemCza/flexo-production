/**
 * Współdzielone elementy backendu Flexo.
 * <ul>
 *   <li><b>exception</b> – wyjątki używane w modułach domenowych (ResourceNotFoundException → 404, ConflictException → 409).</li>
 *   <li><b>globalexceptionhandler</b> – centralna obsługa wyjątków i format odpowiedzi błędu (ErrorResponse).</li>
 * </ul>
 * Serwisy powinny rzucać ResourceNotFoundException zamiast RuntimeException przy braku zasobu.
 */
package com.flexo.common;
