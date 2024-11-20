new Swiper(".swiper-category-popup", {
  slidesPerView: 4,
  loopedSlides: 4,
  centeredSlides: false,
  spaceBetween: 10,
  grabCursor: true,
  loop: true,
  pagination: ".swiper-pagination",
  paginationClickable: true,
  breakpoints: {
    1200: {
      slidesPerView: 6,
      loopedSlides: 6,
      spaceBetween: 10,
    },
    1024: {
      slidesPerView: 4,
      loopedSlides: 4,
      spaceBetween: 10,
    },
    768: {
      slidesPerView: 3,
      loopedSlides: 3,
      spaceBetween: 10,
    },
    675: {
      slidesPerView: 2,
      loopedSlides: 2,
      spaceBetween: 20,
    },
  },
});

const elementPrices = document.querySelectorAll(".new-price");
const priceFormats = document.querySelectorAll(".price-format");

[...elementPrices, ...priceFormats].forEach((e) => {
  const valueString = e.textContent || "0";
  e.textContent = Number(valueString).toLocaleString();
});
