import { onMounted, onUnmounted } from 'vue';
export function useScrollAnimation(selector, animationClass = 'fade-in-up-stagger') {
    let observer = null;
    const setupObserver = () => {
        observer = new IntersectionObserver((entries) => {
            entries.forEach((entry, index) => {
                if (entry.isIntersecting) {
                    setTimeout(() => {
                        entry.target.classList.add('visible');
                        entry.target.classList.add(animationClass);
                    }, index * 100); // stagger effect
                    observer?.unobserve(entry.target);
                }
            });
        }, {
            threshold: 0.1,
            rootMargin: '0px 0px -100px 0px',
        });
        const elements = document.querySelectorAll(selector);
        elements.forEach((el) => observer?.observe(el));
    };
    onMounted(() => {
        setupObserver();
    });
    onUnmounted(() => {
        observer?.disconnect();
    });
}
