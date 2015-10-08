package org.anderes.edu.dojo.java8.news.stars;


public class Star {

    private final String system;
    private final String starname;
    private final Double distance;

    private Star(String system, String starname, String distance) {
        this.system = system;
        this.starname = starname;
        this.distance = Double.valueOf(distance);
    }
    
    public static Star create(String system, String starname, String distance) {
        return new Star(system, starname, distance);
    }

    public String getSystem() {
        return system;
    }

    public String getStarname() {
        return starname;
    }

    public Double getDistance() {
        return distance;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((distance == null) ? 0 : distance.hashCode());
        result = prime * result + ((starname == null) ? 0 : starname.hashCode());
        result = prime * result + ((system == null) ? 0 : system.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Star other = (Star) obj;
        if (distance == null) {
            if (other.distance != null)
                return false;
        } else if (!distance.equals(other.distance))
            return false;
        if (starname == null) {
            if (other.starname != null)
                return false;
        } else if (!starname.equals(other.starname))
            return false;
        if (system == null) {
            if (other.system != null)
                return false;
        } else if (!system.equals(other.system))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Star [system=" + system + ", star=" + starname + ", distance=" + distance + "]";
    }

    
}
