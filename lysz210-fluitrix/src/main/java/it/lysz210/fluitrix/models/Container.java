package it.lysz210.fluitrix.models;

import java.util.HashSet;
import java.util.Set;

/**
 * a single cell mai contain more than one drop.
 * For fluitrix should never happen
 * @param droplets
 */
public record Container(
        Set<Droplet> droplets
) {
    public Container() {
        this(new HashSet<>());
    }
    public Container(Set<Droplet> droplets) {
        this.droplets = new HashSet<>(droplets);
    }

    public boolean addDroplet(Droplet droplet) {
        return droplets.add(droplet);
    }

    public boolean removeDroplet(Droplet droplet) {
        return droplets.remove(droplet);
    }

    public boolean isEmpty() {
        return droplets.isEmpty();
    }
}
