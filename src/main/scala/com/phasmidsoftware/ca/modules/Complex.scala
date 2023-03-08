package com.phasmidsoftware.ca.modules

trait Complex {
    def +(other: Complex): Complex

    def magnitude: Double

    def complement: Complex
}

case class ComplexCartesian(r: Double, i: Double) extends Complex {
    def +(other: Complex): Complex = other match {
        case c: ComplexCartesian => ComplexCartesian(r + c.r, i + c.i)
        case c: ComplexPolar => this + ComplexCartesian(c)
    }

    def magnitude: Double = math.sqrt(r * r + i * i)

    def complement: Complex = ComplexCartesian(r, -i)
}

object ComplexCartesian {
    def apply(c: ComplexPolar): Complex = ComplexCartesian(c.r * math.cos(c.theta), c.r * math.sin(c.theta))
}

case class ComplexPolar(r: Double, theta: Double) extends Complex {

    def +(other: Complex): Complex = ComplexCartesian(this) + other

    def magnitude: Double = r

    def complement: Complex = ComplexPolar(r, -theta)
}
